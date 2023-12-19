package com.registraire.tasklet;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.registraire.utils.BatchUtils.ENTREPRISE;
import static com.registraire.utils.BatchUtils.UNABLE_TO_OPEN_FILE;
import static com.registraire.utils.TaskletUtils.COD_DOM_VAL;
import static com.registraire.utils.TaskletUtils.COD_FORME_JURI;
import static com.registraire.utils.TaskletUtils.COD_FORME_JURI_VALUE;
import static com.registraire.utils.TaskletUtils.COD_STAT_IMMAT;
import static com.registraire.utils.TaskletUtils.COD_STAT_IMMAT_VALUE;
import static com.registraire.utils.TaskletUtils.DAT_CESS_PREVU;
import static com.registraire.utils.TaskletUtils.DAT_DEPO_DECLR;
import static com.registraire.utils.TaskletUtils.DOWNLOAD_FILE_PATH;
import static com.registraire.utils.TaskletUtils.DOWNLOAD_FOLDER;
import static com.registraire.utils.TaskletUtils.ENTREPRISE_FILTERED;
import static com.registraire.utils.TaskletUtils.FILE_DELETE_ERROR;
import static com.registraire.utils.TaskletUtils.FILE_DELETE_SUCCESS;
import static com.registraire.utils.TaskletUtils.FILE_RENAMED;
import static com.registraire.utils.TaskletUtils.IND_FAIL;
import static com.registraire.utils.TaskletUtils.IND_FAIL_VALUE;
import static com.registraire.utils.TaskletUtils.NEQ;
import static com.registraire.utils.TaskletUtils.START_DOWNLOAD_FILE;
import static com.registraire.utils.TaskletUtils.TOTAL_RESULT;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_REMOVE_FILE;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_RENAME_FILE;
import static com.registraire.utils.TaskletUtils.UNZIP_WITH_SUCCESS;
import static com.registraire.utils.TaskletUtils.URL_REGISTRE;
import static com.registraire.utils.TaskletUtils.ZIP_FILE_PATH;

@Slf4j
@RequiredArgsConstructor
public class DownloadCSV implements Tasklet {

    private boolean isFileDownloaded = false;
    private final Object lock = new Object();

    private List<String> neqs = new ArrayList<>();
    private List<String> codes = new ArrayList<>();

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("start the tasklet");
        synchronized (lock) {
            if (!isFileDownloaded) {
                downloadFile();
                isFileDownloaded = true;
            }
        }
        handleDownloadedFile();
        // TODO write a method to rewrite all the other CSV files to reduce the search fields
        keepDataEntreprise();
        removeDuplicatedFiles();
        return RepeatStatus.FINISHED;
    }

    private void downloadFile() throws IOException {
        log.info(START_DOWNLOAD_FILE);

        URL url = new URL(URL_REGISTRE);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream inputStream = httpConn.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(DOWNLOAD_FOLDER)) {

                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                log.info("File downloaded to: " + DOWNLOAD_FOLDER);
            }
        } else {
            log.info("HTTP response code: " + responseCode);
            log.info("Unable to download the file. Check the URL or try again later.");
        }
        httpConn.disconnect();
    }

    private void handleDownloadedFile() {
        try (ZipFile zipFile = new ZipFile(ZIP_FILE_PATH)) {
            zipFile.extractAll(DOWNLOAD_FILE_PATH);
            Files.delete(Paths.get(ZIP_FILE_PATH));
            log.info(FILE_DELETE_SUCCESS);
        } catch (IOException e) {
            log.error(FILE_DELETE_ERROR);
        }
        log.info(UNZIP_WITH_SUCCESS);
    }

    // the condition "count <= TOTAL_RESULT" is optional, use only for test
    private void keepDataEntreprise() {
        try (CSVReader reader = new CSVReader(new FileReader(ENTREPRISE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(ENTREPRISE_FILTERED))) {
            String[] line;
            int count = 0;
            while ((line = reader.readNext()) != null && count <= TOTAL_RESULT) {
                if ((isEmptyOrNull(line[IND_FAIL]) && line[IND_FAIL].equals(IND_FAIL_VALUE)) &&
                        isEmptyOrNull(line[DAT_CESS_PREVU]) &&
                        COD_STAT_IMMAT_VALUE.equals(line[COD_STAT_IMMAT]) &&
                        !COD_FORME_JURI_VALUE.equals(line[COD_FORME_JURI]) &&
                        isValidDate(line[DAT_DEPO_DECLR])) {
                    writer.write(String.join(",", line));
                    writer.newLine();
                    neqs.add(line[NEQ]);
                    codes.add(line[COD_DOM_VAL]);
                    count++;
                }
            }
        } catch (IOException | CsvValidationException e) {
            log.error(UNABLE_TO_OPEN_FILE);
        }
    }

    // filter all the other files


    private void removeDuplicatedFiles() {
        try {
            Files.delete(Paths.get(ENTREPRISE));
            File fileToRename = new File(ENTREPRISE_FILTERED);
            boolean isRenamed = fileToRename.renameTo(new File(ENTREPRISE));

            if (isRenamed) {
                log.info(FILE_RENAMED);
            } else {
                log.info(UNABLE_TO_RENAME_FILE);
            }
        } catch (IOException e) {
            log.error(UNABLE_TO_REMOVE_FILE);
        }
    }

    private boolean isEmptyOrNull(String columnValue) {
        return columnValue == null || columnValue.isEmpty();
    }

    private boolean isValidDate(String dateStr) {
        try {
            if (!isEmptyOrNull(dateStr)) {
                LocalDate.parse(dateStr);
                return true;
            }
        } catch (DateTimeParseException ex) {
            log.warn("Invalid date format: " + dateStr);
        }
        return false;
    }
}
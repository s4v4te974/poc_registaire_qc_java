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

import static com.registraire.utils.BatchUtils.ENTREPRISE;
import static com.registraire.utils.BatchUtils.NAME;
import static com.registraire.utils.BatchUtils.UNABLE_TO_OPEN_FILE;
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
import static com.registraire.utils.TaskletUtils.START_DOWNLOAD_FILE;
import static com.registraire.utils.TaskletUtils.TOTAL_RESULT;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_REMOVE_FILE;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_RENAME_FILE;
import static com.registraire.utils.TaskletUtils.UNZIP_WITH_SUCCESS;
import static com.registraire.utils.TaskletUtils.URL_REGISTRE;
import static com.registraire.utils.TaskletUtils.ZIP_FILE_PATH;

@Slf4j
@RequiredArgsConstructor
public class downloadCSV implements Tasklet {

    private boolean isFileDownloaded = false;
    private final Object lock = new Object();

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
        removeUnecessaryLines();
        removeDuplicatedFile();
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

    // this method is optional, use only for test
    private void removeUnecessaryLines() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ENTREPRISE_FILTERED))) {
            CSVReader reader = new CSVReader(new FileReader(NAME));
            String[] line;
            int count = 0;
            while ((line = reader.readNext()) != null && count <= TOTAL_RESULT) {
                if ((line[IND_FAIL] == null || line[IND_FAIL].equals(IND_FAIL_VALUE)) &&
                        (line[DAT_CESS_PREVU] == null || line[DAT_CESS_PREVU].isEmpty()) &&
                        line[COD_STAT_IMMAT].equals(COD_STAT_IMMAT_VALUE) &&
                        !line[COD_FORME_JURI].equals(COD_FORME_JURI_VALUE) &&
                        LocalDate.parse(line[DAT_DEPO_DECLR]).isAfter(LocalDate.of(2013, 12, 31))) {

                    writer.write(String.join(",", line));
                    writer.newLine();
                    count++;
                }
            }
        } catch (IOException | CsvValidationException e) {
            log.error(UNABLE_TO_OPEN_FILE);
        }
    }

    private void removeDuplicatedFile(){
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
}
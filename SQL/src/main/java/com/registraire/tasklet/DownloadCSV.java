package com.registraire.tasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.registraire.utils.TaskletUtils.DOWNLOAD_FILE_PATH;
import static com.registraire.utils.TaskletUtils.DOWNLOAD_FOLDER;
import static com.registraire.utils.TaskletUtils.FILE_DELETE_ERROR;
import static com.registraire.utils.TaskletUtils.FILE_DELETE_SUCCESS;
import static com.registraire.utils.TaskletUtils.START_DOWNLOAD_FILE;
import static com.registraire.utils.TaskletUtils.UNZIP_WITH_SUCCESS;
import static com.registraire.utils.TaskletUtils.URL_REGISTRE;
import static com.registraire.utils.TaskletUtils.ZIP_FILE_PATH;

@Slf4j
@RequiredArgsConstructor
public class DownloadCSV implements Tasklet {

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
}
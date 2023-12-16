package com.registraire.step.afterstep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.registraire.utils.TaskletUtils.DOWNLOAD_FILE_PATH;

@Component
@Slf4j
public class RemoveAllFilesListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        Path folder = Paths.get(DOWNLOAD_FILE_PATH);
        try {
            Files.walk(folder).filter(Files::isRegularFile).forEach(file ->
            {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    log.error("unable to remove files");
                }
            });
        } catch (IOException e) {
            log.error("unable to read the folder");
        }
        log.info("Step execution completed.");
        return null;
    }
}

package com.registraire.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.registraire.utils.TaskletUtils.DOWNLOAD_FILE_PATH;

@Component
@Slf4j
public class RemoveAllFiles implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
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

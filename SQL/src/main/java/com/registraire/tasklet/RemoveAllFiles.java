package com.registraire.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.registraire.utils.TaskletUtils.DOWNLOAD_FILE_PATH;
import static com.registraire.utils.TaskletUtils.FILE_RENAMED;
import static com.registraire.utils.TaskletUtils.FILTERED;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_REMOVE_FILE;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_RENAME_FILE;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_RETRIEVE_LIST_OF_FILES;

@Component
@Slf4j
public class RemoveAllFiles implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        List<String> fileNames = filesInFolder();
        for(String file :fileNames){
            try {
                Files.delete(Paths.get(file));
            } catch (IOException e) {
                log.error(UNABLE_TO_REMOVE_FILE, file);
            }
        }
        log.info("Step execution completed.");
        return RepeatStatus.FINISHED;
    }

    private List<String> filesInFolder() {
        List<File> files = new ArrayList<>(Arrays.asList(
                Objects.requireNonNull(new File(DOWNLOAD_FILE_PATH).listFiles())));
        if (!files.isEmpty()) {
            return files.stream().map(File::getPath).toList();
        }
        log.error(UNABLE_TO_RETRIEVE_LIST_OF_FILES);
        return Collections.emptyList();
    }
}

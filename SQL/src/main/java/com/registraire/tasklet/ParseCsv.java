package com.registraire.tasklet;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.registraire.service.ConversionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.registraire.utils.BatchUtils.DOMAINE_PATH;
import static com.registraire.utils.BatchUtils.ENTREPRISE_PATH;
import static com.registraire.utils.TaskletUtils.COD_DOM_VAL;
import static com.registraire.utils.TaskletUtils.COD_FORME_JURI;
import static com.registraire.utils.TaskletUtils.COD_FORME_JURI_VALUE;
import static com.registraire.utils.TaskletUtils.COD_STAT_IMMAT;
import static com.registraire.utils.TaskletUtils.COD_STAT_IMMAT_VALUE;
import static com.registraire.utils.TaskletUtils.CSV_PARSE_WITH_ERROR;
import static com.registraire.utils.TaskletUtils.CSV_PARSE_WITH_SUCCESS;
import static com.registraire.utils.TaskletUtils.DAT_CESS_PREVU;
import static com.registraire.utils.TaskletUtils.DAT_DEPO_DECLR;
import static com.registraire.utils.TaskletUtils.DOMAINE_NAME;
import static com.registraire.utils.TaskletUtils.DOWNLOAD_FILE_PATH;
import static com.registraire.utils.TaskletUtils.ENTREPRISE_NAME;
import static com.registraire.utils.TaskletUtils.FILE_RENAMED;
import static com.registraire.utils.TaskletUtils.FILTERED;
import static com.registraire.utils.TaskletUtils.IND_FAIL;
import static com.registraire.utils.TaskletUtils.IND_FAIL_VALUE;
import static com.registraire.utils.TaskletUtils.NEQ;
import static com.registraire.utils.TaskletUtils.POINT;
import static com.registraire.utils.TaskletUtils.TOTAL_RESULT;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_REMOVE_FILE;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_RENAME_FILE;
import static com.registraire.utils.TaskletUtils.UNABLE_TO_RETRIEVE_LIST_OF_FILES;

@Slf4j
public class ParseCsv implements Tasklet {

    @Autowired
    private ConversionService conversionService;

    private Map<String, String[]> entrepriseByNeqs = new ConcurrentHashMap<>();
    private Map<String, String[]> entrepriseByCodes = new ConcurrentHashMap<>();

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        keepDataEntreprise();

        List<String> files = filesInFolder();
        List<String> toBeParsedByNeqs = files.stream()
                .filter(s -> !s.contains(ENTREPRISE_NAME) && !s.contains(DOMAINE_NAME))
                .toList();
        toBeParsedByNeqs.forEach(this::filterCsvByNeqs);
        filterCsvByCodes();

        removeDuplicatedFiles();
        return RepeatStatus.FINISHED;
    }

    // the condition "count <= TOTAL_RESULT" is optional, use only for test
    private void keepDataEntreprise() {
        String fileOutput = buildTempFile(ENTREPRISE_PATH);
        try (CSVReader reader = new CSVReader(new FileReader(ENTREPRISE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput))) {
            String[] line;
            int count = 0;
            while ((line = reader.readNext()) != null && count <= TOTAL_RESULT) {
                line = conversionService.handleSpecialCharacters(line);
                if ((isEmptyOrNull(line[IND_FAIL]) || line[IND_FAIL].equals(IND_FAIL_VALUE)) &&
                        isEmptyOrNull(line[DAT_CESS_PREVU]) &&
                        COD_STAT_IMMAT_VALUE.equals(line[COD_STAT_IMMAT]) &&
                        !COD_FORME_JURI_VALUE.equals(line[COD_FORME_JURI]) &&
                        isValidDate(line[DAT_DEPO_DECLR])) {
                    writer.write(String.join(",", line));
                    writer.newLine();
                    count++;

                    entrepriseByNeqs.put(line[NEQ], line);
                    entrepriseByCodes.put(line[COD_DOM_VAL], line);
                }
            }
            log.info(CSV_PARSE_WITH_SUCCESS, ENTREPRISE_PATH);
        } catch (IOException | CsvValidationException e) {
            log.error(CSV_PARSE_WITH_ERROR, ENTREPRISE_PATH);
        }
    }

    private void filterCsvByNeqs(String path) {
        String fileOutput = buildTempFile(path);
        try (CSVReader reader = new CSVReader(new FileReader(path));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                line = conversionService.handleSpecialCharacters(line);
                if (entrepriseByNeqs.containsKey(line[0])) {
                    writer.write(String.join(",", line));
                    writer.newLine();
                }
            }
            log.info(CSV_PARSE_WITH_SUCCESS, path);
        } catch (IOException | CsvValidationException e) {
            log.error(CSV_PARSE_WITH_ERROR, path);
        }
    }

    private void filterCsvByCodes() {
        String fileOutput = buildTempFile(DOMAINE_PATH);
        try (CSVReader reader = new CSVReader(new FileReader(DOMAINE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (entrepriseByCodes.containsKey(line[1])) {
                    writer.write(String.join(",", line));
                    writer.newLine();
                }
            }
            log.info("CSV {} parse with success", DOMAINE_PATH);
        } catch (IOException | CsvValidationException e) {
            log.error("CSV {} parse error", DOMAINE_PATH);
        }
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

    private void removeDuplicatedFiles() {
        List<String> fileNames = filesInFolder().stream().filter(s -> !s.contains(FILTERED)).toList();
        for(String file :fileNames){
            try {
                Files.delete(Paths.get(file));
                File fileToRename = new File(buildFiltered(file));
                boolean isRenamed = fileToRename.renameTo(new File(file));

                if (isRenamed) {
                    log.info(FILE_RENAMED);
                } else {
                    log.info(UNABLE_TO_RENAME_FILE, file);
                }
            } catch (IOException e) {
                log.error(UNABLE_TO_REMOVE_FILE, file);
            }
        }
    }


    private boolean isEmptyOrNull(String columnValue) {
        return columnValue == null || columnValue.isEmpty();
    }

    private boolean isValidDate(String dateStr) {
        try {
            if (!isEmptyOrNull(dateStr)) {
                return true;
            }
        } catch (DateTimeParseException ex) {
            log.warn("Invalid date format: " + dateStr);
        }
        return false;
    }

    private String buildTempFile(String fileName) {
        String endFiles = "-filtered.csv";
        return fileName.substring(0, fileName.length() - 4).concat(endFiles);
    }

    private String buildFiltered(String fileName){
        int extension = fileName.indexOf(POINT);
        return new StringBuilder(fileName).insert(extension, FILTERED).toString();
    }


}

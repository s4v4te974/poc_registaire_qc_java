package com.registraire.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.registraire.model.RegistraireDto;
import com.registraire.service.RetrieveSector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;

import static com.registraire.utils.BatchUtils.DOMAINE;
import static com.registraire.utils.BatchUtils.FINISH_POPULATE;
import static com.registraire.utils.BatchUtils.START_READING;
import static com.registraire.utils.BatchUtils.UNABLE_TO_OPEN_FILE;
import static com.registraire.utils.BatchUtils.UNABLE_TO_VALIDATE_CSV;

@Slf4j
@Service
public class RetrieveSectorImpl implements RetrieveSector {
    @Override
    public void populateSector(RegistraireDto dto) {
        try {

            log.info(START_READING, DOMAINE);

            CSVReader reader = new CSVReader(new FileReader(DOMAINE));
            String[] line;

            if(dto.getPrimaryActivityCode() != null){
                while ((line = reader.readNext()) != null) {
                    if (dto.getPrimaryActivityCode().equals(line[1])) {
                        dto.setPrimaryActivitySector(line[2]);
                        break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            log.error(UNABLE_TO_OPEN_FILE + DOMAINE);
        } catch (CsvValidationException e) {
            log.error(UNABLE_TO_VALIDATE_CSV + DOMAINE);
        }
    }
}

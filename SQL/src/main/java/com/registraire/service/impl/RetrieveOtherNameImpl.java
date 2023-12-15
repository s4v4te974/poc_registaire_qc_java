package com.registraire.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.registraire.model.RegistraireDto;
import com.registraire.service.RetrieveOtherName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;

import static com.registraire.utils.BatchUtils.FINISH_POPULATE;
import static com.registraire.utils.BatchUtils.MULTIPLE_SEPARATOR;
import static com.registraire.utils.BatchUtils.NAME;
import static com.registraire.utils.BatchUtils.START_READING;
import static com.registraire.utils.BatchUtils.UNABLE_TO_OPEN_FILE;
import static com.registraire.utils.BatchUtils.UNABLE_TO_VALIDATE_CSV;

@Slf4j
@Service
public class RetrieveOtherNameImpl implements RetrieveOtherName {
    @Override
    public void  populateOtherName(RegistraireDto dto) {
        try {

            CSVReader reader = new CSVReader(new FileReader(NAME));
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (dto.getNeq().equals(line[0])) {
                    if(dto.getOtherNameEn() == null){
                        dto.setOtherNameEn(line[2]);
                    }else{
                        dto.setOtherNameEn(dto.getOtherNameEn().concat(MULTIPLE_SEPARATOR).concat(line[2]));
                    }

                    if (dto.getOtherName() == null) {
                        dto.setOtherName(line[1]);
                    } else {
                        dto.setOtherName(dto.getOtherName().concat(MULTIPLE_SEPARATOR).concat(line[1]));
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            log.error(UNABLE_TO_OPEN_FILE + NAME);
        } catch (CsvValidationException e) {
            log.error(UNABLE_TO_VALIDATE_CSV + NAME);
        }
    }
}

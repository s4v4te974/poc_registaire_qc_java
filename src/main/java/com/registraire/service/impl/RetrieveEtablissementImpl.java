package com.registraire.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.registraire.model.RegistraireDto;
import com.registraire.service.RetrieveEtablissement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;

import static com.registraire.utils.BatchUtils.ETABLISSEMENT;
import static com.registraire.utils.BatchUtils.FINISH_POPULATE;
import static com.registraire.utils.BatchUtils.MULTIPLE_SEPARATOR;
import static com.registraire.utils.BatchUtils.SEPARATOR;
import static com.registraire.utils.BatchUtils.START_READING;
import static com.registraire.utils.BatchUtils.UNABLE_TO_OPEN_FILE;
import static com.registraire.utils.BatchUtils.UNABLE_TO_VALIDATE_CSV;

@Slf4j
@Service
public class RetrieveEtablissementImpl implements RetrieveEtablissement {

    @Override
    public void populateEtablissement(RegistraireDto dto) {
        try {
            log.info(START_READING, ETABLISSEMENT);

            CSVReader reader = new CSVReader(new FileReader(ETABLISSEMENT));
            String[] line;
            while ((line = reader.readNext()) != null) {
                if(dto.getNeq().equals(line[0])){
                    String adress = buildAdress(line[6], line[7], line[8], line[9]);
                    if(dto.getEtablissement() == null){
                        dto.setEtablissement(adress);
                    }else{
                        dto.setEtablissement(dto.getEtablissement().concat(MULTIPLE_SEPARATOR).concat(adress));
                    }
                }
            }
            reader.close();
        } catch (IOException ioe) {
            log.error(UNABLE_TO_OPEN_FILE + ETABLISSEMENT);
        } catch (CsvValidationException e) {
            log.error( UNABLE_TO_VALIDATE_CSV + ETABLISSEMENT);
        }
    }

    private String buildAdress(String adr1, String adr2, String adr3, String adr4){
        StringBuilder str = new StringBuilder(adr1).append(SEPARATOR)
                .append(adr2).append(SEPARATOR)
                .append(adr3).append(SEPARATOR)
                .append(adr4);
        return str.toString();
    }
}

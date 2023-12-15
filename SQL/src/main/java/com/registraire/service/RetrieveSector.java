package com.registraire.service;

import com.opencsv.exceptions.CsvValidationException;
import com.registraire.model.RegistraireDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public interface RetrieveSector {

    public void populateSector(RegistraireDto dto);

}

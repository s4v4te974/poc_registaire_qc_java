package com.registraire.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface ConversionService {

    int parseColumnToInt(String column);

    LocalDate parseColumnToDate(String column);

    char parseColumnToChar(String column);

}

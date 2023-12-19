package com.registraire.service.impl;

import com.registraire.service.ConversionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
@Slf4j
public class ConversionServiceImpl implements ConversionService {

    @Override
    public int parseColumnToInt(String column) {
        if (column != null && !column.isEmpty()) {
            try {
                return Integer.parseInt(column);
            } catch (NumberFormatException e) {
                log.error("");
            }
        }
        return -1;
    }

    @Override
    public LocalDate parseColumnToDate(String column) {
        if (column != null && !column.isEmpty()) {
            try {
                return LocalDate.parse(column);
            } catch (DateTimeParseException e) {
                log.error("");
            }
        }
        return LocalDate.now();
    }

    @Override
    public char parseColumnToChar(String column) {
        return column != null && !StringUtils.isEmpty(column) ? column.charAt(0) : ' ';
    }
}

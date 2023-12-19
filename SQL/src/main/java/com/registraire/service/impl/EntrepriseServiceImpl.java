package com.registraire.service.impl;

import com.registraire.model.Entreprise;
import com.registraire.service.ConversionService;
import com.registraire.service.EntrepriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    @Autowired
    private ConversionService conversionService;

    @Override
    public DefaultLineMapper<Entreprise> defaultLineMapper() {
        DefaultLineMapper<Entreprise> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper());
        return lineMapper;
    }

    @Override
    public FieldSetMapper<Entreprise> fieldSetMapper() {
        return fieldSet -> new Entreprise(
                fieldSet.readString(0),
                fieldSet.readString(1),
                conversionService.parseColumnToDate(fieldSet.readString(2)),
                fieldSet.readString(3),
                fieldSet.readString(4),
                conversionService.parseColumnToDate(fieldSet.readString(5)),
                fieldSet.readString(6),
                fieldSet.readString(7),
                conversionService.parseColumnToDate(fieldSet.readString(8)),
                fieldSet.readString(9),
                conversionService.parseColumnToDate(fieldSet.readString(10)),
                conversionService.parseColumnToInt(fieldSet.readString(11)),
                conversionService.parseColumnToInt(fieldSet.readString(12)),
                conversionService.parseColumnToDate(fieldSet.readString(13)),
                conversionService.parseColumnToInt(fieldSet.readString(14)),
                conversionService.parseColumnToDate(fieldSet.readString(15)),
                conversionService.parseColumnToDate(fieldSet.readString(16)),
                fieldSet.readString(17),
                conversionService.parseColumnToInt(fieldSet.readString(18)),
                fieldSet.readString(19),
                fieldSet.readString(20),
                conversionService.parseColumnToInt(fieldSet.readString(21)),
                fieldSet.readString(22),
                fieldSet.readString(23),
                conversionService.parseColumnToDate(fieldSet.readString(24)),
                conversionService.parseColumnToChar(fieldSet.readString(25)),
                conversionService.parseColumnToChar(fieldSet.readString(26)),
                conversionService.parseColumnToChar(fieldSet.readString(27)),
                conversionService.parseColumnToDate(fieldSet.readString(28)),
                conversionService.parseColumnToDate(fieldSet.readString(29)),
                fieldSet.readString(30),
                fieldSet.readString(31),
                conversionService.parseColumnToChar(fieldSet.readString(32)),
                fieldSet.readString(33),
                fieldSet.readString(34),
                fieldSet.readString(35),
                fieldSet.readString(36));
    }
}
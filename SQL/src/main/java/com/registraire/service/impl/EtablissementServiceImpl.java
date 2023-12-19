package com.registraire.service.impl;

import com.registraire.model.Etablissement;
import com.registraire.service.ConversionService;
import com.registraire.service.EtablissementService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtablissementServiceImpl implements EtablissementService {

    @Autowired
    private ConversionService conversionService;

    @Override
    public DefaultLineMapper<Etablissement> defaultlineMapper() {
        DefaultLineMapper<Etablissement> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper());
        return lineMapper;
    }

    @Override
    public FieldSetMapper<Etablissement> fieldSetMapper() {
        return fieldSet -> new Etablissement(
                fieldSet.readString(0),
                conversionService.parseColumnToInt(fieldSet.readString(1)),
                conversionService.parseColumnToChar(fieldSet.readString(2)),
                conversionService.parseColumnToChar(fieldSet.readString(3)),
                conversionService.parseColumnToChar(fieldSet.readString(4)),
                conversionService.parseColumnToChar(fieldSet.readString(5)),
                fieldSet.readString(6),
                fieldSet.readString(7),
                fieldSet.readString(8),
                fieldSet.readString(9),
                fieldSet.readString(10),
                fieldSet.readString(11),
                conversionService.parseColumnToInt(fieldSet.readString(12)),
                fieldSet.readString(13),
                fieldSet.readString(14));
    }
}

package com.registraire.service.impl;

import com.registraire.model.FusionScission;
import com.registraire.service.ConversionService;
import com.registraire.service.FusionScissionService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FusionScissionServiceImpl implements FusionScissionService {

    @Autowired
    private ConversionService conversionService;

    @Override
    public DefaultLineMapper<FusionScission> defaultlineMapper() {
        DefaultLineMapper<FusionScission> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper());
        return lineMapper;
    }

    @Override
    public FieldSetMapper<FusionScission> fieldSetMapper() {
        return fieldSet ->
                new FusionScission(
                        fieldSet.readString(0),
                        fieldSet.readString(1),
                        fieldSet.readString(2),
                        fieldSet.readString(3),
                        conversionService.parseColumnToDate(fieldSet.readString(4)),
                        conversionService.parseColumnToChar(fieldSet.readString(5)),
                        fieldSet.readString(6),
                        fieldSet.readString(7));
    }
}

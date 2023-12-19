package com.registraire.service.impl;

import com.registraire.model.ContinuationTransformation;
import com.registraire.service.ContiTransfoService;
import com.registraire.service.ConversionService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContiTransfoServiceImpl implements ContiTransfoService {

    @Autowired
    private ConversionService conversionService;

    @Override
    public DefaultLineMapper<ContinuationTransformation> defaultLineMapper() {
        DefaultLineMapper<ContinuationTransformation> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper());
        return lineMapper;
    }

    @Override
    public FieldSetMapper<ContinuationTransformation> fieldSetMapper() {
        return fieldSet -> new ContinuationTransformation(
                fieldSet.readString(0),
                fieldSet.readString(1),
                fieldSet.readString(2),
                fieldSet.readString(3),
                fieldSet.readString(4),
                conversionService.parseColumnToDate(fieldSet.readString(5)));
    }
}

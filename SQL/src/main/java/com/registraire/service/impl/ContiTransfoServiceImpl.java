package com.registraire.service.impl;

import com.registraire.model.ContinuationTransformation;
import com.registraire.service.ContiTransfoService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ContiTransfoServiceImpl implements ContiTransfoService {

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
                LocalDate.parse(fieldSet.readString(5)));
    }
}

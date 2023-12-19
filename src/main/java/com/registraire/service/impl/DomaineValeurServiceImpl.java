package com.registraire.service.impl;

import com.registraire.model.DomaineValeur;
import com.registraire.service.DomaineValeurService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Service;

@Service
public class DomaineValeurServiceImpl implements DomaineValeurService {
    @Override
    public DefaultLineMapper<DomaineValeur> defaultLineMapper() {
        DefaultLineMapper<DomaineValeur> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper());
        return lineMapper;
    }

    @Override
    public FieldSetMapper<DomaineValeur> fieldSetMapper() {
        return fieldSet -> new DomaineValeur(
                fieldSet.readString(0),
                fieldSet.readString(1),
                fieldSet.readString(2));
    }
}

package com.registraire.service.impl;

import com.registraire.model.Nom;
import com.registraire.service.NomService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NomServiceImpl implements NomService {
    @Override
    public DefaultLineMapper<Nom> defaultlineMapper() {
        DefaultLineMapper<Nom> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper());
        return lineMapper;
    }

    @Override
    public FieldSetMapper<Nom> fieldSetMapper() {
        return fieldSet -> {
            return new Nom(
                    fieldSet.readString(0),
                    fieldSet.readString(1),
                    fieldSet.readString(2),
                    fieldSet.readString(3),
                    fieldSet.readString(4),
                    LocalDate.parse(fieldSet.readString(5)),
                    LocalDate.parse(fieldSet.readString(6)));
        };
    }
}

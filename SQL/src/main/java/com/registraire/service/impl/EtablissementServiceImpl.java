package com.registraire.service.impl;

import com.registraire.model.Etablissement;
import com.registraire.service.EtablissementService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class EtablissementServiceImpl implements EtablissementService {
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
        return fieldSet -> {
            return new Etablissement(fieldSet.readString(0),
                    fieldSet.readInt(1),
                    fieldSet.readChar(2),
                    fieldSet.readChar(3),
                    fieldSet.readChar(4),
                    fieldSet.readChar(5),
                    fieldSet.readString(6),
                    fieldSet.readString(7),
                    fieldSet.readString(8),
                    fieldSet.readString(9),
                    fieldSet.readString(10),
                    fieldSet.readString(11),
                    fieldSet.readInt(12),
                    fieldSet.readString(13),
                    fieldSet.readString(14));
        };
    }
}

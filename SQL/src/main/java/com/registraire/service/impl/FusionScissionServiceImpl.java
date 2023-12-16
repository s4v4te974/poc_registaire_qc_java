package com.registraire.service.impl;

import com.registraire.model.Etablissement;
import com.registraire.model.FusionScission;
import com.registraire.model.Nom;
import com.registraire.service.FusionScissionService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FusionScissionServiceImpl implements FusionScissionService {
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
        return fieldSet -> {
            return new FusionScission(
                    fieldSet.readString(0),
                    fieldSet.readString(1),
                    fieldSet.readString(2),
                    fieldSet.readString(3),
                    LocalDate.parse(fieldSet.readString(4)),
                    fieldSet.readChar(5),
                    fieldSet.readString(6),
                    fieldSet.readString(7));
        };
    }
}

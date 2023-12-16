package com.registraire.service.impl;

import com.registraire.model.Entreprise;
import com.registraire.service.EntrepriseService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EntrepriseServiceImpl implements EntrepriseService {


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
        return fieldSet -> {
            return new Entreprise(
                    fieldSet.readString(0),
                    fieldSet.readString(1),
                    LocalDate.parse(fieldSet.readString(2)),
                    fieldSet.readString(3),
                    fieldSet.readString(4),
                    LocalDate.parse(fieldSet.readString(5)),
                    fieldSet.readString(6),
                    fieldSet.readString(7),
                    LocalDate.parse(fieldSet.readString(8)),
                    fieldSet.readString(9),
                    LocalDate.parse(fieldSet.readString(10)),
                    fieldSet.readInt(11),
                    fieldSet.readInt(12),
                    LocalDate.parse(fieldSet.readString(13)),
                    fieldSet.readInt(14),
                    LocalDate.parse(fieldSet.readString(15)),
                    LocalDate.parse(fieldSet.readString(16)),
                    fieldSet.readString(17),
                    fieldSet.readInt(18),
                    fieldSet.readString(19),
                    fieldSet.readString(20),
                    fieldSet.readInt(21),
                    fieldSet.readString(22),
                    fieldSet.readString(23),
                    LocalDate.parse(fieldSet.readString(24)),
                    fieldSet.readChar(25),
                    fieldSet.readChar(26),
                    fieldSet.readChar(27),
                    LocalDate.parse(fieldSet.readString(28)),
                    LocalDate.parse(fieldSet.readString(29)),
                    fieldSet.readString(30),
                    fieldSet.readString(31),
                    fieldSet.readChar(32),
                    fieldSet.readString(33),
                    fieldSet.readString(34),
                    fieldSet.readString(35),
                    fieldSet.readString(36));
        };
    }
}

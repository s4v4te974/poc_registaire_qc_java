package com.registraire.service.impl;

import com.registraire.model.RegistraireDto;
import com.registraire.service.ReaderService;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.registraire.utils.BatchUtils.ACT_ECO;
import static com.registraire.utils.BatchUtils.ADRESS_FOUR;
import static com.registraire.utils.BatchUtils.ADRESS_ONE;
import static com.registraire.utils.BatchUtils.ADRESS_THREE;
import static com.registraire.utils.BatchUtils.ADRESS_TWO;
import static com.registraire.utils.BatchUtils.CODE_PRIMARY;
import static com.registraire.utils.BatchUtils.NEQ;
import static com.registraire.utils.BatchUtils.SEPARATOR;

@Component
public class ReaderServiceImpl implements ReaderService {


    @Override
    public DefaultLineMapper<RegistraireDto> lineMapper() {
        DefaultLineMapper<RegistraireDto> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        tokenizer.setIncludedFields(0, 17, 19, 33, 34, 35, 36);
        tokenizer.setNames(NEQ, CODE_PRIMARY, ACT_ECO, ADRESS_ONE, ADRESS_TWO, ADRESS_THREE, ADRESS_FOUR);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper());
        return lineMapper;
    }

    @Override
    public FieldSetMapper<RegistraireDto> fieldSetMapper() {
        return fieldSet -> {
            String address = fieldSet.readString(ADRESS_ONE) + SEPARATOR +
                    fieldSet.readString(ADRESS_TWO) + SEPARATOR +
                    fieldSet.readString(ADRESS_THREE) + SEPARATOR +
                    fieldSet.readString(ADRESS_FOUR);

            return RegistraireDto.builder()
                    .uuid(UUID.randomUUID()) //
                    .neq(fieldSet.readString(NEQ)) //
                    .primaryActivityCode(fieldSet.readString(CODE_PRIMARY)) //
                    .type(fieldSet.readString(ACT_ECO)) //
                    .primaryAdress(address) //
                    .build();
        };
    }
}

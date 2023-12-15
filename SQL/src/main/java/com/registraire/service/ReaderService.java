package com.registraire.service;

import com.registraire.model.RegistraireDto;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.stereotype.Component;

@Component
public interface ReaderService {

    DefaultLineMapper<RegistraireDto> lineMapper();

    FieldSetMapper<RegistraireDto> fieldSetMapper();
}

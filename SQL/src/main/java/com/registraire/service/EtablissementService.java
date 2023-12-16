package com.registraire.service;

import com.registraire.model.Etablissement;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.stereotype.Component;

@Component
public interface EtablissementService {

    DefaultLineMapper<Etablissement> defaultlineMapper();

    FieldSetMapper<Etablissement> fieldSetMapper();
}

package com.registraire.service;

import com.registraire.model.Etablissement;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface EtablissementService {

    DefaultLineMapper<Etablissement> defaultlineMapper();

    FieldSetMapper<Etablissement> fieldSetMapper();
}

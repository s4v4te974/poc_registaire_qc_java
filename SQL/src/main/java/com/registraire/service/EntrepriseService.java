package com.registraire.service;

import com.registraire.model.Entreprise;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.stereotype.Component;

@Component
public interface EntrepriseService {

    DefaultLineMapper<Entreprise> defaultLineMapper();

    FieldSetMapper<Entreprise> fieldSetMapper();
}

package com.registraire.service;

import com.registraire.model.Nom;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.stereotype.Service;

@Service
public interface NomService {

    DefaultLineMapper<Nom> defaultlineMapper();

    FieldSetMapper<Nom> fieldSetMapper();
}

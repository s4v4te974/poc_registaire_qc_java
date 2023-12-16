package com.registraire.service;

import com.registraire.model.DomaineValeur;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;

public interface DomaineValeurService {
    DefaultLineMapper<DomaineValeur> defaultLineMapper();

    FieldSetMapper<DomaineValeur> fieldSetMapper();
}

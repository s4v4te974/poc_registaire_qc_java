package com.registraire.service;

import com.registraire.model.FusionScission;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.stereotype.Service;

@Service
public interface FusionScissionService {
    DefaultLineMapper<FusionScission> defaultlineMapper();

    FieldSetMapper<FusionScission> fieldSetMapper();
}

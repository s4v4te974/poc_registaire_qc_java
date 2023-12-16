package com.registraire.service;

import com.registraire.model.FusionScission;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;

public interface FusionScissionService {
    DefaultLineMapper<FusionScission> defaultlineMapper();

    FieldSetMapper<FusionScission> fieldSetMapper();
}

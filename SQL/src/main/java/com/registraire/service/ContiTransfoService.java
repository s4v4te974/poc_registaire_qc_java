package com.registraire.service;

import com.registraire.model.ContinuationTransformation;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;

public interface ContiTransfoService {

    DefaultLineMapper<ContinuationTransformation> defaultLineMapper();

    FieldSetMapper<ContinuationTransformation> fieldSetMapper();

}

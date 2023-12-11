package com.registraire.step.processor;

import com.registraire.mapper.RegistraireMapper;
import com.registraire.model.RegistraireDto;
import com.registraire.model.RegistraireEntity;
import com.registraire.service.RetrieveEtablissement;
import com.registraire.service.RetrieveOtherName;
import com.registraire.service.RetrieveSector;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.registraire.utils.BatchUtils.END_PROCESSING;
import static com.registraire.utils.BatchUtils.START_PROCESSING;


@Slf4j
@Component
public class CustomProcessor implements ItemProcessor<RegistraireDto, RegistraireEntity> {

    public CustomProcessor() {
    }

    @Autowired
    private RetrieveSector retrieveSector;

    @Autowired
    private RetrieveOtherName retrieveOtherName;

    @Autowired
    private RetrieveEtablissement retrieveEtablissement;


    @Override
    public RegistraireEntity process(RegistraireDto item) throws Exception {

        RegistraireMapper mapper = Mappers.getMapper(RegistraireMapper.class);

        retrieveSector.populateSector(item);

        retrieveOtherName.populateOtherName(item);

        retrieveEtablissement.populateEtablissement(item);

        return mapper.mapToEntity(item);
    }
}

package com.registraire.step.reader;

import com.registraire.model.Etablissement;
import com.registraire.service.EtablissementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.registraire.utils.BatchUtils.ETABLISSEMENT;

@Component
@RequiredArgsConstructor
@Slf4j
public class EtablissementReader extends FlatFileItemReader<Etablissement> {

    EtablissementService lineMapper;

    public FlatFileItemReader<Etablissement> readerEtablissement(){
        log.info("Etablissement reader");
        return new FlatFileItemReaderBuilder<Etablissement>()
                .name("readEntreprise")
                .resource(new FileSystemResource(ETABLISSEMENT))
                .linesToSkip(1)
                .lineMapper(lineMapper.defaultlineMapper())
                .build();
    }

}

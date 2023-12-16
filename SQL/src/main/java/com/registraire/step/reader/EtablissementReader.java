package com.registraire.step.reader;

import com.registraire.model.Etablissement;
import com.registraire.service.EtablissementService;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import static com.registraire.utils.BatchUtils.ETABLISSEMENT;

public class EtablissementReader extends FlatFileItemReader<Etablissement> {

    @Autowired
    EtablissementService lineMapper;

    public FlatFileItemReader<Etablissement> readerEtablissement(){
        return new FlatFileItemReaderBuilder<Etablissement>()
                .name("readEntreprise")
                .resource(new FileSystemResource(ETABLISSEMENT))
                .linesToSkip(1)
                .lineMapper(lineMapper.defaultlineMapper())
                .build();
    }

}

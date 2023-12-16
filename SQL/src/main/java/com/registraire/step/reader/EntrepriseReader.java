package com.registraire.step.reader;

import com.registraire.model.Entreprise;
import com.registraire.service.EntrepriseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.registraire.utils.BatchUtils.ENTREPRISE;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntrepriseReader extends FlatFileItemReader<Entreprise> {

    private EntrepriseService lineMapper;

    public FlatFileItemReader<Entreprise> readerEntreprise(){
        log.info("Entreprise reader");
        return new FlatFileItemReaderBuilder<Entreprise>()
                .name("readEntreprise")
                .resource(new FileSystemResource(ENTREPRISE))
                .linesToSkip(1)
                .lineMapper(lineMapper.defaultLineMapper())
                .build();
    }
}

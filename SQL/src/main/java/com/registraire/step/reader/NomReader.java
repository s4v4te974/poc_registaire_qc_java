package com.registraire.step.reader;

import com.registraire.model.Nom;
import com.registraire.service.NomService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.registraire.utils.BatchUtils.NAME;

@Component
@RequiredArgsConstructor
public class NomReader extends FlatFileItemReader<Nom> {

    private NomService nomService;

    public FlatFileItemReader<Nom> nomReader() {
        return new FlatFileItemReaderBuilder<Nom>()
                .name("nomReader")
                .resource(new FileSystemResource(NAME))
                .linesToSkip(1)
                .lineMapper(nomService.defaultlineMapper())
                .build();
    }
}
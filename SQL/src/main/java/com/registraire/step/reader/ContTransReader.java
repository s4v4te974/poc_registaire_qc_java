package com.registraire.step.reader;

import com.registraire.model.ContinuationTransformation;
import com.registraire.service.ContiTransfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.registraire.utils.BatchUtils.CONTINUATIONS_TRANSFORMATIONS;

@Component
@RequiredArgsConstructor
public class ContTransReader extends FlatFileItemReader<ContinuationTransformation> {

    private ContiTransfoService contiTransfoService;

    public FlatFileItemReader<ContinuationTransformation> readerEntreprise() {
        return new FlatFileItemReaderBuilder<ContinuationTransformation>()
                .name("readContinuation")
                .resource(new FileSystemResource(CONTINUATIONS_TRANSFORMATIONS))
                .linesToSkip(1)
                .lineMapper(contiTransfoService.defaultLineMapper())
                .build();
    }
}
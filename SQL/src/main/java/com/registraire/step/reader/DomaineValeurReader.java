package com.registraire.step.reader;

import com.registraire.model.DomaineValeur;
import com.registraire.service.DomaineValeurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.registraire.utils.BatchUtils.DOMAINE;

@Component
@RequiredArgsConstructor
@Slf4j
public class DomaineValeurReader extends FlatFileItemReader<DomaineValeur> {

    private DomaineValeurService domaineValeurService;

    public FlatFileItemReader<DomaineValeur> readerDomaine(){
        log.info("DomaineValeur reader");
        return new FlatFileItemReaderBuilder<DomaineValeur>()
                .name("readDomaine")
                .resource(new FileSystemResource(DOMAINE))
                .linesToSkip(1)
                .lineMapper(domaineValeurService.defaultLineMapper())
                .build();
    }

}

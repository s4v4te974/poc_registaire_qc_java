package com.registraire.step.reader;

import com.registraire.model.FusionScission;
import com.registraire.service.FusionScissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.registraire.utils.BatchUtils.FUSION_SCISSION;

@Component
@RequiredArgsConstructor
public class FusionScissionReader extends FlatFileItemReader<FusionScission> {

    private FusionScissionService fusionScissionService;

    public FlatFileItemReader<FusionScission> readFusion(){
        return new FlatFileItemReaderBuilder<FusionScission>()
                .name("readFusion")
                .resource(new FileSystemResource(FUSION_SCISSION))
                .linesToSkip(1)
                .lineMapper(fusionScissionService.defaultlineMapper())
                .build();
    }
}
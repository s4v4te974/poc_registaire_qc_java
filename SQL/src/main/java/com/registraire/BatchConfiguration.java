package com.registraire;

import com.registraire.listener.CustomListener;
import com.registraire.model.RegistraireDto;
import com.registraire.model.RegistraireEntity;
import com.registraire.service.ReaderService;
import com.registraire.step.processor.CustomProcessor;
import com.registraire.tasklet.MyTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static com.registraire.utils.BatchUtils.ENTREPRISE;
import static com.registraire.utils.BatchUtils.START_PROCESSING;

@Configuration
@Slf4j
@ComponentScan
public class BatchConfiguration {

    @Autowired
    ReaderService readerService;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public FlatFileItemReader<RegistraireDto> reader() {
        return new FlatFileItemReaderBuilder<RegistraireDto>()
                .name("customReader")
                .resource(new FileSystemResource(ENTREPRISE))
                .linesToSkip(1)
                .lineMapper(readerService.lineMapper())
                .fieldSetMapper(readerService.fieldSetMapper())
                .build();
    }

    @Bean
    public CustomProcessor customProcessor() {
        log.info(START_PROCESSING);
        return new CustomProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<RegistraireEntity> writer(DataSource dataSource) {
        log.info("Starting the writer");
        return new JdbcBatchItemWriterBuilder<RegistraireEntity>()
                .sql("INSERT INTO REGISTRAIRE (UUID, NEQ, TYPE, PRIMARY_ADRESS, PRIMARY_ACTIVITY_SECTOR, OTHER_NAME, OTHER_NAME_EN, ETABLISSEMENT) " +
                                "VALUES (:uuid, :neq, :type, :primaryAdress, :primaryActivitySector, :otherName, :otherNameEn, :etablissement) " +
                                "ON CONFLICT (NEQ) DO UPDATE " +
                                "SET " +
                                "UUID = EXCLUDED.UUID, " +
                                "TYPE = EXCLUDED.TYPE, " +
                                "PRIMARY_ADRESS = EXCLUDED.PRIMARY_ADRESS, " +
                                "PRIMARY_ACTIVITY_SECTOR = EXCLUDED.PRIMARY_ACTIVITY_SECTOR, " +
                                "OTHER_NAME = EXCLUDED.OTHER_NAME, " +
                                "OTHER_NAME_EN = EXCLUDED.OTHER_NAME_EN, " +
                                "ETABLISSEMENT = EXCLUDED.ETABLISSEMENT;")
                .dataSource(dataSource)
                .beanMapped().build();
    }

    @Bean
    public Tasklet task() {
        return new MyTasklet();
    }

    @Bean
    public Step downloadAndProcessStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("downloadAndProcessStep", jobRepository)
                .tasklet(task(), transactionManager)
                .build();
    }

    @Bean
    public Step processCsv(FlatFileItemReader<RegistraireDto> reader, CustomProcessor processor,
                           JdbcBatchItemWriter<RegistraireEntity> writer, JobRepository jobRepository,
                           PlatformTransactionManager transactionManager) {
        return new StepBuilder("processCsv", jobRepository)
                .<RegistraireDto, RegistraireEntity>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job processCsvFile(Step downloadAndProcessStep, Step processCsv, CustomListener listener, JobRepository jobRepository) {
        return new JobBuilder("ExtractData", jobRepository)
                .listener(listener)
                .start(downloadAndProcessStep)
                .next(processCsv)
                .build();
    }
}
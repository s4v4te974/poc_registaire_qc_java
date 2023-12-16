package com.registraire;

import com.registraire.listener.CustomListener;
import com.registraire.model.Entreprise;
import com.registraire.model.Etablissement;
import com.registraire.step.processor.EtablissementProcessor;
import com.registraire.step.reader.EntrepriseReader;
import com.registraire.step.reader.EtablissementReader;
import com.registraire.step.writer.EntrepriseWriter;
import com.registraire.step.writer.EtablissementWriter;
import com.registraire.tasklet.downloadCSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@Slf4j
@ComponentScan
public class BatchConfiguration {


    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    // download
    @Bean
    public Tasklet task() {
        return new downloadCSV();
    }

    @Bean
    public Step downloadAndProcessTasklet(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("downloadAndProcessTasklet", jobRepository)
                .tasklet(task(), transactionManager)
                .build();
    }

    // entreprise part
    @Bean
    public FlatFileItemReader<Entreprise> entrepriseFlatFileItemReader() {
        log.info("Entreprise reader");
        return new EntrepriseReader().readerEntreprise();
    }

    @Bean
    public JdbcBatchItemWriter<Entreprise> entrepriseJdbcBatchItemWriter(DataSource dataSource) {
        log.info("Entreprise writer");
        return new EntrepriseWriter().entrepriseWriter(dataSource);
    }

    @Bean
    public Step filterAndSaveEntreprise(FlatFileItemReader<Entreprise> reader,
                                        JdbcBatchItemWriter<Entreprise> writer, JobRepository jobRepository,
                                        PlatformTransactionManager transactionManager) {
        return new StepBuilder("filterAndSaveEntreprise", jobRepository)
                .<Entreprise, Entreprise>chunk(10, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    // etablissement part
    @Bean
    public FlatFileItemReader<Etablissement> etablissementFlatFileItemReader() {
        log.info("Etablissement reader");
        return new EtablissementReader().readerEtablissement();
    }

    @Bean
    public ItemProcessor<Etablissement, Etablissement> etablissementItemProcessor(){
        log.info("etablissement processor");
        return new EtablissementProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Etablissement> etablissementJdbcBatchItemWriter(DataSource dataSource) {
        log.info("Etablissement writer");
        return new EtablissementWriter().etablissementWriter(dataSource);
    }






    @Bean
    public Step filterAndSaveEtablissement(FlatFileItemReader<Etablissement> reader,
                                           JdbcBatchItemWriter<Etablissement> writer, JobRepository jobRepository,
                                        PlatformTransactionManager transactionManager) {
        return new StepBuilder("filterAndSaveEtablissement", jobRepository)
                .<Entreprise, Entreprise>chunk(10, transactionManager)
                .reader(reader)
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
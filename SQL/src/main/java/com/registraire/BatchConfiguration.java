package com.registraire;

import com.registraire.listener.CustomListener;
import com.registraire.model.ContinuationTransformation;
import com.registraire.model.DomaineValeur;
import com.registraire.model.Entreprise;
import com.registraire.model.Etablissement;
import com.registraire.model.FusionScission;
import com.registraire.model.Nom;
import com.registraire.service.ContiTransfoService;
import com.registraire.service.DomaineValeurService;
import com.registraire.service.EntrepriseService;
import com.registraire.service.EtablissementService;
import com.registraire.service.FusionScissionService;
import com.registraire.service.NomService;
import com.registraire.step.processor.ContinuationTransformationProcessor;
import com.registraire.step.processor.DomaineValeurProcessor;
import com.registraire.step.processor.EtablissementProcessor;
import com.registraire.step.processor.FusionScissionProcessor;
import com.registraire.step.processor.NomProcessor;
import com.registraire.tasklet.downloadCSV;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static com.registraire.utils.BatchUtils.CONTINUATIONS_TRANSFORMATIONS;
import static com.registraire.utils.BatchUtils.CONTINUATION_TRANSFORMATION_SQL;
import static com.registraire.utils.BatchUtils.DOMAINE;
import static com.registraire.utils.BatchUtils.DOMAINE_VALEUR_SQL;
import static com.registraire.utils.BatchUtils.ENTREPRISE;
import static com.registraire.utils.BatchUtils.ENTREPRISE_SQL;
import static com.registraire.utils.BatchUtils.ETABLISSEMENT;
import static com.registraire.utils.BatchUtils.ETABLISSEMENT_SQL;
import static com.registraire.utils.BatchUtils.FUSION_SCISSION;
import static com.registraire.utils.BatchUtils.FUS_SCI_SQL;
import static com.registraire.utils.BatchUtils.NAME;
import static com.registraire.utils.BatchUtils.NOM_SQL;

@Configuration
@Slf4j
@ComponentScan
@RequiredArgsConstructor
public class BatchConfiguration {

    @Autowired
    private ContiTransfoService contiTransfoService;

    @Autowired
    private DomaineValeurService domaineValeurService;

    @Autowired
    private EntrepriseService entrepriseService;

    @Autowired
    private EtablissementService etablissementService;

    @Autowired
    private FusionScissionService fusionScissionService;

    @Autowired
    private NomService nomService;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    // download begin
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
    // download end

    // ContinuationTransfo begin

    @Bean
    public FlatFileItemReader<ContinuationTransformation> contTransfoFlatFileItemReader() {
        return new FlatFileItemReaderBuilder<ContinuationTransformation>()
                .name("readContinuation")
                .resource(new FileSystemResource(CONTINUATIONS_TRANSFORMATIONS))
                .linesToSkip(1)
                .lineMapper(contiTransfoService.defaultLineMapper())
                .build();
    }

    @Bean
    @Primary
    public ItemProcessor<ContinuationTransformation, ContinuationTransformation> contTransfItemProcessor() {
        return new ContinuationTransformationProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<ContinuationTransformation> contTransfJdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ContinuationTransformation>()
                .sql(CONTINUATION_TRANSFORMATION_SQL)
                .dataSource(dataSource)
                .beanMapped().build();
    }

    @Bean
    public Step filterAndSaveContiTransfo(FlatFileItemReader<ContinuationTransformation> contiReader,
                                          ItemProcessor<ContinuationTransformation, ContinuationTransformation> contiProcessor,
                                          JdbcBatchItemWriter<ContinuationTransformation> contiWriter,
                                          JobRepository jobRepository,
                                          PlatformTransactionManager transactionManager) {
        return new StepBuilder("filterAndSaveContiTransfo", jobRepository)
                .<ContinuationTransformation, ContinuationTransformation>chunk(1000, transactionManager)
                .reader(contiReader)
                .processor(contiProcessor)
                .writer(contiWriter)
                .build();
    }
    // ContinuationTransfo end

    // DomaineValeur begin
    @Bean
    public FlatFileItemReader<DomaineValeur> domaineValueFlatFileItemReader() {
        log.info("DomaineValeur reader");
        return new FlatFileItemReaderBuilder<DomaineValeur>()
                .name("readDomaine")
                .resource(new FileSystemResource(DOMAINE))
                .linesToSkip(1)
                .lineMapper(domaineValeurService.defaultLineMapper())
                .build();
    }

    @Bean
    @Primary
    public ItemProcessor<DomaineValeur, DomaineValeur> domaineValuefItemProcessor() {
        return new DomaineValeurProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<DomaineValeur> domaineValeurJdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<DomaineValeur>()
                .sql(DOMAINE_VALEUR_SQL)
                .dataSource(dataSource)
                .beanMapped().build();
    }

    @Bean
    public Step filterAndSaveDomaineValeur(FlatFileItemReader<DomaineValeur> domaineReader,
                                           ItemProcessor<DomaineValeur, DomaineValeur> domaineProcessor,
                                           JdbcBatchItemWriter<DomaineValeur> domaineWriter,
                                           JobRepository jobRepository,
                                           PlatformTransactionManager transactionManager) {
        return new StepBuilder("filterAndSaveDomaineValeur", jobRepository)
                .<DomaineValeur, DomaineValeur>chunk(1000, transactionManager)
                .reader(domaineReader)
                .processor(domaineProcessor)
                .writer(domaineWriter)
                .build();
    }
    // DomaineValeur end

    // entreprise begin
    @Bean
    public FlatFileItemReader<Entreprise> entrepriseFlatFileItemReader() {
        return new FlatFileItemReaderBuilder<Entreprise>()
                .name("readEntreprise")
                .resource(new FileSystemResource(ENTREPRISE))
                .linesToSkip(1)
                .lineMapper(entrepriseService.defaultLineMapper())
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Entreprise> entrepriseJdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Entreprise>()
                .sql(ENTREPRISE_SQL)
                .dataSource(dataSource)
                .beanMapped().build();
    }

    @Bean
    public Step filterAndSaveEntreprise(FlatFileItemReader<Entreprise> reader,
                                        JdbcBatchItemWriter<Entreprise> writer, JobRepository jobRepository,
                                        PlatformTransactionManager transactionManager) {
        return new StepBuilder("filterAndSaveEntreprise", jobRepository)
                .<Entreprise, Entreprise>chunk(100, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }
    // entreprise end

    // etablissement part
    @Bean
    public FlatFileItemReader<Etablissement> etablissementFlatFileItemReader() {
        return new FlatFileItemReaderBuilder<Etablissement>()
                .name("readEntreprise")
                .resource(new FileSystemResource(ETABLISSEMENT))
                .linesToSkip(1)
                .lineMapper(etablissementService.defaultlineMapper())
                .build();
    }

    @Bean
    @Primary
    public ItemProcessor<Etablissement, Etablissement> etablissementItemProcessor() {
        return new EtablissementProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Etablissement> etablissementJdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Etablissement>()
                .sql(ETABLISSEMENT_SQL)
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Step filterAndSaveEtablissement(FlatFileItemReader<Etablissement> etabReader,
                                           ItemProcessor<Etablissement, Etablissement> etabProcessor,
                                           JdbcBatchItemWriter<Etablissement> etabWriter,
                                           JobRepository jobRepository,
                                           PlatformTransactionManager transactionManager) {
        return new StepBuilder("filterAndSaveEtablissement", jobRepository)
                .<Etablissement, Etablissement>chunk(1000, transactionManager)
                .reader(etabReader)
                .processor(etabProcessor)
                .writer(etabWriter)
                .build();
    }
    // etablissement end

    // FusionScission part
    @Bean
    public FlatFileItemReader<FusionScission> fusionScissionItemReader() {
        return new FlatFileItemReaderBuilder<FusionScission>()
                .name("readFusion")
                .resource(new FileSystemResource(FUSION_SCISSION))
                .linesToSkip(1)
                .lineMapper(fusionScissionService.defaultlineMapper())
                .build();
    }

    @Bean
    @Primary
    public ItemProcessor<FusionScission, FusionScission> fusionScissionItemProcessor() {
        return new FusionScissionProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<FusionScission> fusionScissionJdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<FusionScission>()
                .sql(FUS_SCI_SQL)
                .dataSource(dataSource)
                .beanMapped().build();
    }

    @Bean
    public Step filterAndSaveFuSci(FlatFileItemReader<FusionScission> fuSciReader,
                                            ItemProcessor<FusionScission, FusionScission> fuSciProcessor,
                                            JdbcBatchItemWriter<FusionScission> fuSciWriter,
                                            JobRepository jobRepository,
                                            PlatformTransactionManager transactionManager) {
        return new StepBuilder("filterAndSaveFuSci", jobRepository)
                .<FusionScission, FusionScission>chunk(1000, transactionManager)
                .reader(fuSciReader)
                .processor(fuSciProcessor)
                .writer(fuSciWriter)
                .build();
    }
    // FusionScission end

    // Nom part
    @Bean
    public FlatFileItemReader<Nom> nomFlatFileItemReader() {
        return new FlatFileItemReaderBuilder<Nom>()
                .name("nomReader")
                .resource(new FileSystemResource(NAME))
                .linesToSkip(1)
                .lineMapper(nomService.defaultlineMapper())
                .build();
    }

    @Bean
    public ItemProcessor<Nom, Nom> nomItemProcessor() {
        return new NomProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Nom> nomJdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Nom>()
                .sql(NOM_SQL)
                .dataSource(dataSource)
                .beanMapped().build();
    }

    @Bean
    public Step filterAndSaveName(FlatFileItemReader<Nom> nomReader,
                                  ItemProcessor<Nom, Nom> nomProcessor,
                                  JdbcBatchItemWriter<Nom> nomWriter,
                                  JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager) {
        return new StepBuilder("filterAndSaveName", jobRepository)
                .<Nom, Nom>chunk(1000, transactionManager)
                .reader(nomReader)
                .processor(nomProcessor)
                .writer(nomWriter)
                .build();
    }
    // Nom end

    @Bean
    public Job processCsvFile(@Qualifier("downloadAndProcessTasklet") Step downloadAndProcessTasklet,
                              @Qualifier("filterAndSaveEntreprise") Step filterAndSaveEntreprise,
                              @Qualifier("filterAndSaveContiTransfo") Step filterAndSaveContiTransfo,
                              @Qualifier("filterAndSaveDomaineValeur") Step filterAndSaveDomaineValeur,
                              @Qualifier("filterAndSaveEtablissement") Step filterAndSaveEtablissement,
                              @Qualifier("filterAndSaveFuSci") Step filterAndSaveFuSci,
                              @Qualifier("filterAndSaveName") Step filterAndSaveName,
                              CustomListener listener,
                              JobRepository jobRepository) {
        return new JobBuilder("ExtractData", jobRepository)
                .listener(listener)
                .start(downloadAndProcessTasklet)
                .next(filterAndSaveEntreprise)
                .next(filterAndSaveFuSci)
                .next(filterAndSaveDomaineValeur)
                .next(filterAndSaveContiTransfo)
                .next(filterAndSaveEtablissement)
                .next(filterAndSaveName)
                .build();
    }
}
package com.registraire.step.writer;

import com.registraire.model.ContinuationTransformation;
import com.registraire.model.DomaineValeur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class ContiTransWriter extends JdbcBatchItemWriter<ContinuationTransformation> {

    public JdbcBatchItemWriter<ContinuationTransformation> contiTransWriter (DataSource dataSource){
        log.info("ContinuationTransformation writer");
        return new JdbcBatchItemWriterBuilder<ContinuationTransformation>()
                .sql("INSERT INTO ContinuationTransformation (neq, cod_typ_chang, cod_regim_juri, autr_regim_juri, nom_loclt, dat_efctvt)" +
                        "VALUES (:neq, :codTypChang, :codRegimJuri, :autrRegimJuri, :nomLoclt, :datEfctvt)" +
                        "ON CONFLICT (neq) DO UPDATE" +
                        "SET cod_typ_chang = EXCLUDED.cod_typ_chang," +
                        "    cod_regim_juri = EXCLUDED.cod_regim_juri," +
                        "    autr_regim_juri = EXCLUDED.autr_regim_juri," +
                        "    nom_loclt = EXCLUDED.nom_loclt," +
                        "    dat_efctvt = EXCLUDED.dat_efctvt;")
                .dataSource(dataSource)
                .beanMapped().build();
    }
}
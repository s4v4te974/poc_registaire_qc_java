package com.registraire.step.writer;

import com.registraire.model.DomaineValeur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class DomaineValeurWriter extends JdbcBatchItemWriter<DomaineValeur> {

    public JdbcBatchItemWriter<DomaineValeur> domaineValueWriter(DataSource dataSource) {
        log.info("DomaineValeur writer");
        return new JdbcBatchItemWriterBuilder<DomaineValeur>()
                .sql("INSERT INTO DomaineValeur (typ_dom_val, cod_dom_val, val_dom_fran)" +
                        "VALUES (:typDomVal, :codDomVal, :valDomFran)" +
                        "ON CONFLICT (cod_dom_val) DO UPDATE" +
                        "SET typ_dom_val = EXCLUDED.typ_dom_val," +
                        "    val_dom_fran = EXCLUDED.val_dom_fran;")
                .dataSource(dataSource)
                .beanMapped().build();
    }
}
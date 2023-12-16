package com.registraire.step.writer;

import com.registraire.model.Nom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class NomWriter extends JdbcBatchItemWriter<Nom> {

    public JdbcBatchItemWriter<Nom> nomWriter(DataSource dataSource){
        log.info("Nom writer");
        return new JdbcBatchItemWriterBuilder<Nom>()
                .sql("INSERT INTO Nom (neq, nom_assuj, nom_assuj_lang_etrng, stat_nom, typ_nom_assuj, dat_init_nom_assuj, dat_fin_nom_assuj)" +
                        "VALUES (:neq, :nomAssuj, :nomAssujLangEtrng, :statNom, :typNomAssuj, :datInitNomAssuj, :datFinNomAssuj)" +
                        "ON CONFLICT (neq) DO UPDATE" +
                        "SET nom_assuj = EXCLUDED.nom_assuj," +
                        "    nom_assuj_lang_etrng = EXCLUDED.nom_assuj_lang_etrng," +
                        "    stat_nom = EXCLUDED.stat_nom," +
                        "    typ_nom_assuj = EXCLUDED.typ_nom_assuj," +
                        "    dat_init_nom_assuj = EXCLUDED.dat_init_nom_assuj," +
                        "    dat_fin_nom_assuj = EXCLUDED.dat_fin_nom_assuj;")
                .dataSource(dataSource)
                .beanMapped().build();
    }
}

package com.registraire.step.writer;

import com.registraire.model.Etablissement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class EtablissementWriter extends JdbcBatchItemWriter<Etablissement> {

    public JdbcBatchItemWriter<Etablissement> etablissementWriter(DataSource datasource) {
        log.info("Etablissement writer");
        return new JdbcBatchItemWriterBuilder<Etablissement>()
                .sql("INSERT INTO your_table_name (" +
                        "    NEQ, NO_SUF_ETAB, IND_ETAB_PRINC, IND_SALON_BRONZ, IND_VENTE_TABAC_DETL, IND_DISP," +
                        "    LIGN1_ADR, LIGN2_ADR, LIGN3_ADR, LIGN4_ADR, COD_ACT_ECON, DESC_ACT_ECON_ETAB," +
                        "    NO_ACT_ECON_ETAB, COD_ACT_ECON2, DESC_ACT_ECON_ETAB2" +
                        ") VALUES (" +
                        "    :neq, :noSufEtab, :indEtabPrinc, :indSalonBronz, :indVenteTabacDetl, :indDisp," +
                        "    :lign1Adr, :lign2Adr, :lign3Adr, :lign4Adr, :codActEcon, :descActEconEtab," +
                        "    :noActEconEtab, :codActEcon2, :descActEconEtab2" +
                        ") ON CONFLICT (NEQ) DO UPDATE SET" +
                        "    NO_SUF_ETAB = EXCLUDED.NO_SUF_ETAB," +
                        "    IND_ETAB_PRINC = EXCLUDED.IND_ETAB_PRINC," +
                        "    IND_SALON_BRONZ = EXCLUDED.IND_SALON_BRONZ," +
                        "    IND_VENTE_TABAC_DETL = EXCLUDED.IND_VENTE_TABAC_DETL," +
                        "    IND_DISP = EXCLUDED.IND_DISP," +
                        "    LIGN1_ADR = EXCLUDED.LIGN1_ADR," +
                        "    LIGN2_ADR = EXCLUDED.LIGN2_ADR," +
                        "    LIGN3_ADR = EXCLUDED.LIGN3_ADR," +
                        "    LIGN4_ADR = EXCLUDED.LIGN4_ADR," +
                        "    COD_ACT_ECON = EXCLUDED.COD_ACT_ECON," +
                        "    DESC_ACT_ECON_ETAB = EXCLUDED.DESC_ACT_ECON_ETAB," +
                        "    NO_ACT_ECON_ETAB = EXCLUDED.NO_ACT_ECON_ETAB," +
                        "    COD_ACT_ECON2 = EXCLUDED.COD_ACT_ECON2," +
                        "    DESC_ACT_ECON_ETAB2 = EXCLUDED.DESC_ACT_ECON_ETAB2;")
                .dataSource(datasource)
                .beanMapped()
                .build();
    }
}

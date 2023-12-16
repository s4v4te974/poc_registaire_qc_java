package com.registraire.step.writer;

import com.registraire.model.FusionScission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class FusSciWriter extends JdbcBatchItemWriter<FusionScission> {

    public JdbcBatchItemWriter<FusionScission> fuSciWriter(DataSource dataSource) {
        log.info("FusionScission writer");
        return new JdbcBatchItemWriterBuilder<FusionScission>()
                .sql("INSERT INTO FusionScission (neq, neq_assuj_rel, denom_soc, cod_rela_assuj, dat_efctvt, ind_disp, lign1_adr, lign2_adr)" +
                        "VALUES (:neq, :neqAssujRel, :denomSoc, :codRelaAssuj, :datEfctvt, :indDisp, :lign1Adr, :lign2Adr)" +
                        "ON CONFLICT (neq) DO UPDATE" +
                        "SET neq_assuj_rel = EXCLUDED.neq_assuj_rel," +
                        "    denom_soc = EXCLUDED.denom_soc," +
                        "    cod_rela_assuj = EXCLUDED.cod_rela_assuj," +
                        "    dat_efctvt = EXCLUDED.dat_efctvt," +
                        "    ind_disp = EXCLUDED.ind_disp," +
                        "    lign1_adr = EXCLUDED.lign1_adr," +
                        "    lign2_adr = EXCLUDED.lign2_adr;")
                .dataSource(dataSource)
                .beanMapped().build();
    }
}
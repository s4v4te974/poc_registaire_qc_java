package com.registraire.step.writer;

import com.registraire.model.Entreprise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class EntrepriseWriter extends JdbcBatchItemWriter<Entreprise> {

    public JdbcBatchItemWriter<Entreprise> entrepriseWriter(DataSource dataSource) {
        log.info("Entreprise writer");
        return new JdbcBatchItemWriterBuilder<Entreprise>()
                .sql("INSERT INTO entreprise (" +
                        "    NEQ, IND_FAIL, DAT_IMMAT, COD_REGIM_JURI, COD_INTVAL_EMPLO_QUE, DAT_CESS_PREVU, COD_STAT_IMMAT," +
                        "    COD_FORME_JURI, DAT_STAT_IMMAT, COD_REGIM_JURI_CONSTI, DAT_DEPO_DECLR, AN_DECL, AN_PROD, DAT_LIMIT_PROD," +
                        "    AN_PROD_PRE, DAT_LIMIT_PROD_PRE, DAT_MAJ_INDEX_NOM, COD_ACT_ECON_CAE, NO_ACT_ECON_ASSUJ, DESC_ACT_ECON_ASSUJ," +
                        "    COD_ACT_ECON_CAE2, NO_ACT_ECON_ASSUJ2, DESC_ACT_ECON_ASSUJ2, NOM_LOCLT_CONSTI, DAT_CONSTI, IND_CONVEN_UNMN_ACTNR," +
                        "    IND_RET_TOUT_POUVR, IND_LIMIT_RESP, DAT_DEB_RESP, DAT_FIN_RESP, OBJET_SOC, NO_MTR_VOLONT, ADR_DOMCL_ADR_DISP," +
                        "    ADR_DOMCL_LIGN1_ADR, ADR_DOMCL_LIGN2_ADR, ADR_DOMCL_LIGN3_ADR, ADR_DOMCL_LIGN4_ADR" +
                        ") VALUES (" +
                        "    :neq, :indFail, :datImmat, :codRegimJuri, :codIntvalEmploQue, :datCessPrevu, :codStatImmat," +
                        "    :codFormeJuri, :datStatImmat, :codRegimJuriConsti, :datDepoDeclr, :anDecl, :anProd, :datLimitProd," +
                        "    :anProdPre, :datLimitProdPre, :datMajIndexNom, :codActEconCae, :noActEconAssuj, :descActEconAssuj," +
                        "    :codActEconCae2, :noActEconAssuj2, :descActEconAssuj2, :nomLocltConsti, :datConsti, :indConvenUmnActnr," +
                        "    :indRetToutPouvr, :indLimitResp, :datDebResp, :datFinResp, :objetSoc, :noMtrVolont, :adrDomclAdrDisp," +
                        "    :adrDomclLign1Adr, :adrDomclLign2Adr, :adrDomclLign3Adr, :adrDomclLign4Adr" +
                        ") ON CONFLICT (NEQ) DO UPDATE SET" +
                        "    IND_FAIL = EXCLUDED.IND_FAIL," +
                        "    DAT_IMMAT = EXCLUDED.DAT_IMMAT," +
                        "    COD_REGIM_JURI = EXCLUDED.COD_REGIM_JURI," +
                        "    COD_INTVAL_EMPLO_QUE = EXCLUDED.COD_INTVAL_EMPLO_QUE," +
                        "    DAT_CESS_PREVU = EXCLUDED.DAT_CESS_PREVU," +
                        "    COD_STAT_IMMAT = EXCLUDED.COD_STAT_IMMAT," +
                        "    COD_FORME_JURI = EXCLUDED.COD_FORME_JURI," +
                        "    DAT_STAT_IMMAT = EXCLUDED.DAT_STAT_IMMAT," +
                        "    COD_REGIM_JURI_CONSTI = EXCLUDED.COD_REGIM_JURI_CONSTI," +
                        "    DAT_DEPO_DECLR = EXCLUDED.DAT_DEPO_DECLR," +
                        "    AN_DECL = EXCLUDED.AN_DECL," +
                        "    AN_PROD = EXCLUDED.AN_PROD," +
                        "    DAT_LIMIT_PROD = EXCLUDED.DAT_LIMIT_PROD," +
                        "    AN_PROD_PRE = EXCLUDED.AN_PROD_PRE," +
                        "    DAT_LIMIT_PROD_PRE = EXCLUDED.DAT_LIMIT_PROD_PRE," +
                        "    DAT_MAJ_INDEX_NOM = EXCLUDED.DAT_MAJ_INDEX_NOM," +
                        "    COD_ACT_ECON_CAE = EXCLUDED.COD_ACT_ECON_CAE," +
                        "    NO_ACT_ECON_ASSUJ = EXCLUDED.NO_ACT_ECON_ASSUJ," +
                        "    DESC_ACT_ECON_ASSUJ = EXCLUDED.DESC_ACT_ECON_ASSUJ," +
                        "    COD_ACT_ECON_CAE2 = EXCLUDED.COD_ACT_ECON_CAE2," +
                        "    NO_ACT_ECON_ASSUJ2 = EXCLUDED.NO_ACT_ECON_ASSUJ2," +
                        "    DESC_ACT_ECON_ASSUJ2 = EXCLUDED.DESC_ACT_ECON_ASSUJ2," +
                        "    NOM_LOCLT_CONSTI = EXCLUDED.NOM_LOCLT_CONSTI," +
                        "    DAT_CONSTI = EXCLUDED.DAT_CONSTI," +
                        "    IND_CONVEN_UNMN_ACTNR = EXCLUDED.IND_CONVEN_UNMN_ACTNR," +
                        "    IND_RET_TOUT_POUVR = EXCLUDED.IND_RET_TOUT_POUVR," +
                        "    IND_LIMIT_RESP = EXCLUDED.IND_LIMIT_RESP," +
                        "    DAT_DEB_RESP = EXCLUDED.DAT_DEB_RESP," +
                        "    DAT_FIN_RESP = EXCLUDED.DAT_FIN_RESP," +
                        "    OBJET_SOC = EXCLUDED.OBJET_SOC," +
                        "    NO_MTR_VOLONT = EXCLUDED.NO_MTR_VOLONT," +
                        "    ADR_DOMCL_ADR_DISP = EXCLUDED.ADR_DOMCL_ADR_DISP," +
                        "    ADR_DOMCL_LIGN1_ADR = EXCLUDED.ADR_DOMCL_LIGN1_ADR," +
                        "    ADR_DOMCL_LIGN2_ADR = EXCLUDED.ADR_DOMCL_LIGN2_ADR," +
                        "    ADR_DOMCL_LIGN3_ADR = EXCLUDED.ADR_DOMCL_LIGN3_ADR," +
                        "    ADR_DOMCL_LIGN4_ADR = EXCLUDED.ADR_DOMCL_LIGN4_ADR;")
                .dataSource(dataSource)
                .beanMapped().build();
    }
}
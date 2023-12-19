package com.registraire.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BatchUtils {

    public static final String ENTREPRISE_PATH = "src/main/download/Entreprise.csv";

    public static final String DOMAINE_PATH = "src/main/download/DomaineValeur.csv";

    public static final String FUSION_SCISSION_PATH = "src/main/download/FusionScissions.csv";

    public static final String CONTINUATIONS_TRANSFORMATIONS_PATH = "src/main/download/ContinuationsTransformations.csv";

    public static final String NAME_PATH = "src/main/download/Nom.csv";

    public static final String ETABLISSEMENT_PATH = "src/main/download/Etablissements.csv";

    public static final String CONTINUATION_TRANSFORMATION_SQL = "INSERT INTO ContinuationTransformation " +
            "(ID, NEQ, COD_TYP_CHANG, COD_REGIM_JURI, AUTR_REGIM_JURI, NOM_LOCLT, DAT_EFCTVT)" +
            "VALUES (uuid_generate_v4(), :neq, :codTypChang, :codRegimJuri, :autrRegimJuri, :nomLoclt, :datEfctvt);";

    public static final String DOMAINE_VALEUR_SQL = "INSERT INTO DomaineValeur " +
            "(ID, TYP_DOM_VAL, COD_DOM_VAL, VAL_DOM_FRAN)" +
            "VALUES (uuid_generate_v4(), :typDomVal, :codDomVal, :valDomFran);";

    public static final String ENTREPRISE_SQL = "INSERT INTO entreprise (ID, " +
            "NEQ, IND_FAIL, DAT_IMMAT, COD_REGIM_JURI, COD_INTVAL_EMPLO_QUE, DAT_CESS_PREVU, COD_STAT_IMMAT, " +
            "COD_FORME_JURI, DAT_STAT_IMMAT, COD_REGIM_JURI_CONSTI, DAT_DEPO_DECLR, AN_DECL, AN_PROD, DAT_LIMIT_PROD, " +
            "AN_PROD_PRE, DAT_LIMIT_PROD_PRE, DAT_MAJ_INDEX_NOM, COD_ACT_ECON_CAE, NO_ACT_ECON_ASSUJ, DESC_ACT_ECON_ASSUJ, " +
            "COD_ACT_ECON_CAE2, NO_ACT_ECON_ASSUJ2, DESC_ACT_ECON_ASSUJ2, NOM_LOCLT_CONSTI, DAT_CONSTI, IND_CONVEN_UNMN_ACTNR, " +
            "IND_RET_TOUT_POUVR, IND_LIMIT_RESP, DAT_DEB_RESP, DAT_FIN_RESP, OBJET_SOC, NO_MTR_VOLONT, ADR_DOMCL_ADR_DISP, " +
            "ADR_DOMCL_LIGN1_ADR, ADR_DOMCL_LIGN2_ADR, ADR_DOMCL_LIGN3_ADR, ADR_DOMCL_LIGN4_ADR) " +
            "VALUES (uuid_generate_v4(), " +
            ":neq, :indFail, :datImmat, :codRegimJuri, :codIntvalEmploQue, :datCessPrevu, :codStatImmat, " +
            ":codFormeJuri, :datStatImmat, :codRegimJuriConsti, :datDepoDeclr, :anDecl, :anProd, :datLimitProd, " +
            ":anProdPre, :datLimitProdPre, :datMajIndexNom, :codActEconCae, :noActEconAssuj, :descActEconAssuj, " +
            ":codActEconCae2, :noActEconAssuj2, :descActEconAssuj2, :nomLocltConsti, :datConsti, :indConvenUmnActnr, " +
            ":indRetToutPouvr, :indLimitResp, :datDebResp, :datFinResp, :objetSoc, :noMtrVolont, :adrDomclAdrDisp, " +
            ":adrDomclLign1Adr, :adrDomclLign2Adr, :adrDomclLign3Adr, :adrDomclLign4Adr); ";

    public static final String ETABLISSEMENT_SQL = "INSERT INTO ETABLISSEMENT ( " +
            "ID, NEQ, NO_SUF_ETAB, IND_ETAB_PRINC, IND_SALON_BRONZ, IND_VENTE_TABAC_DETL, IND_DISP, " +
            "LIGN1_ADR, LIGN2_ADR, LIGN3_ADR, LIGN4_ADR, COD_ACT_ECON, DESC_ACT_ECON_ETAB, " +
            "NO_ACT_ECON_ETAB, COD_ACT_ECON2, DESC_ACT_ECON_ETAB2 " +
            ") VALUES (uuid_generate_v4(), " +
            "    :neq, :noSufEtab, :indEtabPrinc, :indSalonBronz, :indVenteTabacDetl, :indDisp," +
            "    :lign1Adr, :lign2Adr, :lign3Adr, :lign4Adr, :codActEcon, :descActEconEtab," +
            "    :noActEconEtab, :codActEcon2, :descActEconEtab2);";

    public static final String FUS_SCI_SQL = "INSERT INTO FusionScission " +
            "(ID, NEQ, NEQ_ASSUJ_REL, DENOM_SOC, COD_RELA_ASSUJ, DAT_EFCTVT, IND_DISP, LIGN1_ADR, LIGN2_ADR)" +
            "VALUES (uuid_generate_v4(), :neq, :neqAssujRel, :denomSoc, :codRelaAssuj, :datEfctvt, :indDisp, :lign1Adr, :lign2Adr);";

    public static final String NOM_SQL = "INSERT INTO Nom " +
            "(ID, NEQ, NOM_ASSUJ, NOM_ASSUJ_LANG_ETRNG, STAT_NOM, TYP_NOM_ASSUJ, DAT_INIT_NOM_ASSUJ, DAT_FIN_NOM_ASSUJ)" +
            "VALUES (uuid_generate_v4(), :neq, :nomAssuj, :nomAssujLangEtrng, :statNom, :typNomAssuj, :datInitNomAssuj, :datFinNomAssuj);";
}

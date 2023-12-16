package com.registraire.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BatchUtils {

    public static final String ENTREPRISE = "src/main/download/Entreprise.csv";

    public static final String DOMAINE = "src/main/download/DomaineValeur.csv";

    public static final String FUSION_SCISSION = "src/main/download/FusionScissions.csv";

    public static final String CONTINUATIONS_TRANSFORMATIONS = "src/main/download/ContinuationsTransformations.csv";

    public static final String NAME = "src/main/download/Nom.csv";

    public static final String ETABLISSEMENT = "src/main/download/Etablissements.csv";

    public static final String UNABLE_TO_OPEN_FILE = "unable to open the file ";
    public static final String REQUEST_NEQ = "SELECT * FROM ENTREPRISE";

    public static final String CONTINUATION_TRANSFORMATION_SQL = "INSERT INTO ContinuationTransformation (neq, cod_typ_chang, cod_regim_juri, autr_regim_juri, nom_loclt, dat_efctvt)" +
            "VALUES (:neq, :codTypChang, :codRegimJuri, :autrRegimJuri, :nomLoclt, :datEfctvt)" +
            "ON CONFLICT (neq) DO UPDATE" +
            "SET cod_typ_chang = EXCLUDED.cod_typ_chang," +
            "    cod_regim_juri = EXCLUDED.cod_regim_juri," +
            "    autr_regim_juri = EXCLUDED.autr_regim_juri," +
            "    nom_loclt = EXCLUDED.nom_loclt," +
            "    dat_efctvt = EXCLUDED.dat_efctvt;";

    public static final String DOMAINE_VALEUR_SQL = "INSERT INTO DomaineValeur (typ_dom_val, cod_dom_val, val_dom_fran)" +
            "VALUES (:typDomVal, :codDomVal, :valDomFran)" +
            "ON CONFLICT (cod_dom_val) DO UPDATE" +
            "SET typ_dom_val = EXCLUDED.typ_dom_val," +
            "    val_dom_fran = EXCLUDED.val_dom_fran;";

    public static final String ENTREPRISE_SQL = "INSERT INTO entreprise (" +
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
            "    ADR_DOMCL_LIGN4_ADR = EXCLUDED.ADR_DOMCL_LIGN4_ADR;";


    public static final String ETABLISSEMENT_SQL = "INSERT INTO your_table_name (" +
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
            "    DESC_ACT_ECON_ETAB2 = EXCLUDED.DESC_ACT_ECON_ETAB2;";

    public static final String FUS_SCI_SQL = "INSERT INTO FusionScission (neq, neq_assuj_rel, denom_soc, cod_rela_assuj, dat_efctvt, ind_disp, lign1_adr, lign2_adr)" +
            "VALUES (:neq, :neqAssujRel, :denomSoc, :codRelaAssuj, :datEfctvt, :indDisp, :lign1Adr, :lign2Adr)" +
            "ON CONFLICT (neq) DO UPDATE" +
            "SET neq_assuj_rel = EXCLUDED.neq_assuj_rel," +
            "    denom_soc = EXCLUDED.denom_soc," +
            "    cod_rela_assuj = EXCLUDED.cod_rela_assuj," +
            "    dat_efctvt = EXCLUDED.dat_efctvt," +
            "    ind_disp = EXCLUDED.ind_disp," +
            "    lign1_adr = EXCLUDED.lign1_adr," +
            "    lign2_adr = EXCLUDED.lign2_adr;";

    public static final String NOM_SQL = "INSERT INTO Nom (neq, nom_assuj, nom_assuj_lang_etrng, stat_nom, typ_nom_assuj, dat_init_nom_assuj, dat_fin_nom_assuj)" +
            "VALUES (:neq, :nomAssuj, :nomAssujLangEtrng, :statNom, :typNomAssuj, :datInitNomAssuj, :datFinNomAssuj)" +
            "ON CONFLICT (neq) DO UPDATE" +
            "SET nom_assuj = EXCLUDED.nom_assuj," +
            "    nom_assuj_lang_etrng = EXCLUDED.nom_assuj_lang_etrng," +
            "    stat_nom = EXCLUDED.stat_nom," +
            "    typ_nom_assuj = EXCLUDED.typ_nom_assuj," +
            "    dat_init_nom_assuj = EXCLUDED.dat_init_nom_assuj," +
            "    dat_fin_nom_assuj = EXCLUDED.dat_fin_nom_assuj;";

}

package com.registraire.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BatchUtils {

    public static final String ENTREPRISE = "src/main/download/Entreprise.csv";

    public static final String DOMAINE = "src/main/download/DomaineValeur.csv";

    public static final String NAME = "src/main/download/Nom.csv";

    public static final String ETABLISSEMENT = "src/main/download/Etablissements.csv";

    public static final String START_READING = "start reading the {} csv files";

    public static final String FINISH_POPULATE = "finished populate with {}";

    public static final String UNABLE_TO_OPEN_FILE = "unable to open the file ";
    public static final String UNABLE_TO_VALIDATE_CSV = "unable to validate the csv content ";

    public static final String SEPARATOR = ", ";
    public static final String MULTIPLE_SEPARATOR = "; ";

    public static final String START_PROCESSING = "Start the Processor process";
    public static final String END_PROCESSING = "Processor process complete with success";

    public static final String NEQ = "NEQ";
    public static final String ACT_ECO = "DESC_ACT_ECON_ASSUJ";

    public static final String CODE_PRIMARY = "COD_ACT_ECON_CAE";

    public static final String ADRESS_ONE = "ADR_DOMCL_LIGN1_ADR";
    public static final String ADRESS_TWO = "ADR_DOMCL_LIGN2_ADR";
    public static final String ADRESS_THREE = "ADR_DOMCL_LIGN3_ADR";
    public static final String ADRESS_FOUR = "ADR_DOMCL_LIGN4_ADR";
}

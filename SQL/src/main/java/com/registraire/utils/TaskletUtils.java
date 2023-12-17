package com.registraire.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskletUtils {

    public static final String URL_REGISTRE = "https://www.registreentreprises.gouv.qc.ca/RQAnonymeGR/GR/GR03/GR03A2_22A_PIU_RecupDonnPub_PC/FichierDonneesOuvertes.aspx";
    public static final String DOWNLOAD_FOLDER = "src/main/download/JeuxDonnees.zip";

    public static final String START_DOWNLOAD_FILE = "Start downloading the file";

    public static final String ZIP_FILE_PATH = "src/main/download/jeuxDonnees.zip";

    public static final String DOWNLOAD_FILE_PATH = "src/main/download/";

    public static final String UNZIP_WITH_SUCCESS = "unzip completed with success";

    public static final String FILE_DELETE_SUCCESS = "Downloaded file deleted with success";

    public static final String FILE_DELETE_ERROR = "Unable to delete Downloaded file";

    public static final String ENTREPRISE_FILTERED = "src/main/download/Entreprise-filtered.csv";

    public static final String FILE_RENAMED = "file renamed with success";

    public static final String UNABLE_TO_RENAME_FILE = "unable to rename file";

    public static final String UNABLE_TO_REMOVE_FILE = "unable to remove file";

    public static final int TOTAL_RESULT = 200;

    public static final int IND_FAIL = 1;

    public static final int DAT_CESS_PREVU = 5;

    public static final int COD_STAT_IMMAT = 6;

    public static final int COD_FORME_JURI = 7;

    public static final int DAT_DEPO_DECLR = 10;

    public static final String IND_FAIL_VALUE = "N";

    public static final String COD_STAT_IMMAT_VALUE = "IM";
    public static final String COD_FORME_JURI_VALUE = "IND";

}

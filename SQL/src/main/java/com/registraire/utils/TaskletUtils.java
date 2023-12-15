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

    public static int TOTAL_RESULT = 100;

}

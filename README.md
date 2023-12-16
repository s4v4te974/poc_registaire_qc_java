# This batch is a POC to process csv file 

## This POC have 2 folder : 
 - _One using SQL, and writing data in different tables_
 - _One using NoSql, and save data in a single line_


### Input:
_Csv file download from_ 
https://www.registreentreprises.gouv.qc.ca/RQAnonymeGR/GR/GR03/GR03A2_22A_PIU_RecupDonnPub_PC/FichierDonneesOuvertes.aspx 

### Output: 
_Data formatted in a PostgreSql database_

# Description: 
**The batch itself read the csv Files and cross data with the other csv files to keep only some data**

# Batch:
### Tasklet :
- _Download the file_
- _Unzip it_
- _Remove line to keep_ (optionnal)
### Reader:
- _Read rows from the csv files_
### Processor:
- _Process the different CSV files (3/5 in our case) to construct the data to be inserted in DB_ 
### Writer:
- _Write processed data in DB_

# What is missing
Due to the fact that this is a POC, some mandatory programming practice are missing
 _- No Exception handling_ (only just log)
 _- No parallel processing_ 
 _- Database for Batch processing are not well created_ (only just log)

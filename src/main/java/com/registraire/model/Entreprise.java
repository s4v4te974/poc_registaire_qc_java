package com.registraire.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entreprise {
    private String neq;

    private String indFail;

    private LocalDate datImmat;

    private String codRegimJuri;

    private String codIntvalEmploQue;

    private LocalDate datCessPrevu;

    private String codStatImmat;

    private String codFormeJuri;

    private LocalDate datStatImmat;

    private String codRegimJuriConsti;

    private LocalDate datDepoDeclr;

    private int anDecl;

    private int anProd;

    private LocalDate datLimitProd;

    private int anProdPre;

    private LocalDate datLimitProdPre;

    private LocalDate datMajIndexNom;

    private String codActEconCae;

    private int noActEconAssuj;

    private String descActEconAssuj;

    private String codActEconCae2;

    private int noActEconAssuj2;

    private String descActEconAssuj2;

    private String nomLocltConsti;

    private LocalDate datConsti;

    char indConvenUmnActnr;

    char indRetToutPouvr;

    char indLimitResp;

    private LocalDate datDebResp;

    private LocalDate datFinResp;

    private String objetSoc;

    private String noMtrVolont;

    char adrDomclAdrDisp;

    private String adrDomclLign1Adr;

    private String adrDomclLign2Adr;

    private String adrDomclLign3Adr;

    private String adrDomclLign4Adr;
}

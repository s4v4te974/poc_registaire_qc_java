package com.registraire.model;

public record Etablissement(String neq,
                            int noSufEtab,
                            char indEtabPrinc,
                            char indSalonBronz,
                            char indVenteTabacDetl,
                            char indDisp,
                            String lign1Adr,
                            String lign2Adr,
                            String lign3Adr,
                            String lign4Adr,
                            String codActEcon,
                            String descActEconEtab,
                            int noActEconEtab,
                            String codActEcon2,
                            String descActEconEtab2) {
}

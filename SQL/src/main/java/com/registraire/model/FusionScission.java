package com.registraire.model;

import java.time.LocalDate;

public record FusionScission(String neq,
                             String neqAssujRel,
                             String denomSoc,
                             String codRelaAssuj,
                             LocalDate datEfctvt,
                             char indDisp,
                             String lign1Adr,
                             String lign2Adr) {
}

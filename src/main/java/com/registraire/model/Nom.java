package com.registraire.model;

import java.time.LocalDate;

public record Nom(String neq,
                  String nomAssuj,
                  String nomAssujLangEtrng,
                  String statNom,
                  String typNomAssuj,
                  LocalDate datInitNomAssuj,
                  LocalDate datFinNomAssuj) {
}

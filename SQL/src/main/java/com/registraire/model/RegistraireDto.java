package com.registraire.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RegistraireDto{

    private UUID uuid;
    private String neq;
    private String primaryActivityCode;
    private String type;
    private String primaryAdress;
    private String primaryActivitySector;
    private String otherName;
    private String otherNameEn;
    private String etablissement;

}

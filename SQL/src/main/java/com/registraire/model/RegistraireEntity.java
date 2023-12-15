package com.registraire.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "REGISTRAIRE")
public class RegistraireEntity {

    @Id
    private UUID uuid;

    @Column(name = "NEQ", unique = true)
    private String neq;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PRIMARY_ADRESS")
    private String primaryAdress;

    @Column(name = "PRIMARY_ACTIVITY_SECTOR")
    private String primaryActivitySector;

    @Column(name = "OTHER_NAME")
    private String otherName;

    @Column(name ="OTHER_NAME_EN")
    private String otherNameEn;

    @Column(name = "ETABLISSEMENT")
    private String etablissement;

}

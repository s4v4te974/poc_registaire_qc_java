package com.registraire.mapper;

import com.registraire.model.RegistraireDto;
import com.registraire.model.RegistraireEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface RegistraireMapper {

    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "neq", source = "neq")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "primaryAdress", source = "primaryAdress")
    @Mapping(target = "primaryActivitySector", source = "primaryActivitySector")
    @Mapping(target = "otherName", source = "otherName")
    @Mapping(target = "otherNameEn", source = "otherNameEn")
    @Mapping(target = "etablissement", source = "etablissement")
    RegistraireEntity mapToEntity(RegistraireDto dto);

}

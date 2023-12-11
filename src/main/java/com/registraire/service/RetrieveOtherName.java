package com.registraire.service;

import com.registraire.model.RegistraireDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface RetrieveOtherName {

    public void populateOtherName(RegistraireDto dto);

}

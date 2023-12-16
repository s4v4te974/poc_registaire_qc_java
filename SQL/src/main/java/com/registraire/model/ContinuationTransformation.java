package com.registraire.model;

import java.time.LocalDate;

public record ContinuationTransformation(String neq,
                                         String codTypChang,
                                         String codRegimJuri,
                                         String autrRegimJuri,
                                         String nomLoclt,
                                         LocalDate datEfctvt ) {
}

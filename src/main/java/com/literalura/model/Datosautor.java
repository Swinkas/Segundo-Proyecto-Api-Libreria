package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datosautor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer fechaDeNacimiento,
        @JsonAlias("death_year") Integer fechaDeFallecimiento
) {
}
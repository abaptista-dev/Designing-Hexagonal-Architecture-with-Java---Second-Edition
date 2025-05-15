package dev.davivieira.framework.adapters.output.file.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public enum TurnoJson {
    MANHA,
    INTERMEDIARIO_MANHA,
    TARDE,
    INTERMEDIARIO_TARDE,
    NOITE,
    INTEGRAL
    ;
}

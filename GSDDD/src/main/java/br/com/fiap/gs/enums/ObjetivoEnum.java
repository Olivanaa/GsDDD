package br.com.fiap.gs.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ObjetivoEnum {
	UM("OBJETIVO 1"), DOIS("OBJETIVO 2"), TRES("OBJETIVO 3"), QUATRO("OBJETIVO 4");
	
	
    private final String descricao;

    ObjetivoEnum(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static ObjetivoEnum fromDescricao(String descricao) {
        for (ObjetivoEnum tipo : values()) {
            if (tipo.descricao.equals(descricao)) {
                return tipo;
            }
        }
        return null; 
    }
}



package br.com.fiap.gs.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoAlimentacaoEnum {
    CAFE_DA_MANHA("CAFÉ DA MANHÃ"),
    ALMOCO("ALMOÇO"),
    JANTAR("JANTAR"),
    LANCHE_DA_MANHA("LANCHE DA MANHÃ"),
    LANCHE_DA_TARDE("LANCHE DA TARDE"),
    LANCHE_DA_NOITE("LANCHE DA NOITE");

    private final String descricao;

    TipoAlimentacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static TipoAlimentacaoEnum fromDescricao(String descricao) {
        for (TipoAlimentacaoEnum tipo : values()) {
            if (tipo.descricao.equals(descricao)) {
                return tipo;
            }
        }
        return null; // ou lançar uma exceção se desejar
    }
}
	


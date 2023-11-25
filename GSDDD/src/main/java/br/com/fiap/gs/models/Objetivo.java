package br.com.fiap.gs.models;

import br.com.fiap.gs.enums.ObjetivoEnum;

public class Objetivo extends Usuario {

	private int tipo;
	private String descricao;
	private ObjetivoEnum obj;

	public Objetivo(ObjetivoEnum obj) {
		if (obj == ObjetivoEnum.UM) {
			descricao = "Estou grávida";
		} else if (obj == ObjetivoEnum.DOIS) {
			descricao = "Quero melhorar minha saúde";
		} else if (obj == ObjetivoEnum.TRES) {
			descricao = "Quero saber como posso evitar doenças transmissíveis";
		} else {
			descricao = "Quero ter hábitos saudáveis";
		}
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ObjetivoEnum getObj() {
		return obj;
	}

	public void setObj(ObjetivoEnum obj) {
		this.obj = obj;
	}

}

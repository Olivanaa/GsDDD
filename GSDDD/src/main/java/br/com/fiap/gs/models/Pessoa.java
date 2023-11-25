package br.com.fiap.gs.models;

import br.com.fiap.gs.enums.TipoImcEnum;

public class Pessoa {

	private int idP;
	private double peso;
	private double altura;
	private double imc;
	private TipoImcEnum tipoImc;
	private Usuario usuario;

	public double calcularIMC(double peso, double altura) {
		imc = peso / altura * altura;
		return imc;
	}

	public void tipoImc(double imc) {
		if (imc <= 0) {
			tipoImc = TipoImcEnum.NAO_INFORMADO;
		} else if (imc <= 16.9) {
			tipoImc = TipoImcEnum.MUITO_ABAIXO_DO_PESO;
		} else if (imc <= 18.4) {
			tipoImc = TipoImcEnum.ABAIXO_DO_PESO;
		} else if (imc <= 24.9) {
			tipoImc = TipoImcEnum.PESO_NORMAL;
		} else if (imc <= 29.9) {
			tipoImc = TipoImcEnum.SOBREPESO;
		} else if (imc <= 34.9) {
			tipoImc = TipoImcEnum.OBESIDADE_GRAU_I;
		} else if (imc <= 39.9) {
			tipoImc = TipoImcEnum.OBESIDADE_GRAU_II;
		} else {
			tipoImc = TipoImcEnum.OBESIDADE_GRAU_III;
		}
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getImc() {
		return imc;
	}

	public void setImc(double imc) {
		this.imc = imc;
	}

	public TipoImcEnum getTipoImc() {
		return tipoImc;
	}

	public void setTipoImc(TipoImcEnum tipoImc) {
		this.tipoImc = tipoImc;
	}

	public int getIdP() {
		return idP;
	}

	public void setIdP(int idP) {
		this.idP = idP;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

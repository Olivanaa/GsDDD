package br.com.fiap.gs.models;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.com.fiap.gs.enums.TipoAtividadeEnum;

public class AtividadeFisica {

	private int idAF;
	private TipoAtividadeEnum tipo;
	private double duracao;
	private String dataAtiv;
	private Usuario usuario;

	public LocalDate validaData(String dataAtiv) throws ParseException, DateTimeParseException {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dtaAtv;
		dtaAtv = LocalDate.parse(dataAtiv, date);
		return dtaAtv;
	}

	public int getIdAF() {
		return idAF;
	}

	public void setIdAF(int idAF) {
		this.idAF = idAF;
	}

	public TipoAtividadeEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoAtividadeEnum tipo) {
		this.tipo = tipo;
	}

	public double getDuracao() {
		return duracao;
	}

	public void setDuracao(double duracao) {
		this.duracao = duracao;
	}

	public String getDataAtiv() {
		return dataAtiv;
	}

	public void setDataAtiv(String dataAtiv) {
		this.dataAtiv = dataAtiv;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

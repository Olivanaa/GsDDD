package br.com.fiap.gs.models;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Sono {

	private int idSono;
	private double duracao;
	private String dataSono;
	private double meta;
	private Usuario usuario;

	public LocalDate validaData(String dataSono) throws ParseException, DateTimeParseException {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dtaSono;
		dtaSono = LocalDate.parse(dataSono, date);
		return dtaSono;
	}


	public int getIdSono() {
		return idSono;
	}

	public void setIdSono(int idSono) {
		this.idSono = idSono;
	}

	public double getDuracao() {
		return duracao;
	}

	public void setDuracao(double duracao) {
		this.duracao = duracao;
	}

	public String getDataSono() {
		return dataSono;
	}

	public void setDataSono(String dataSono) {
		this.dataSono = dataSono;
	}


	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

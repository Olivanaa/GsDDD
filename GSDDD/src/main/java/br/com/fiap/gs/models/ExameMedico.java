package br.com.fiap.gs.models;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ExameMedico {

	private int IdExame;
	private String nomeExame;
	private String dataExame;
	private String horarioExame;
	private Usuario usuario;

	public LocalDate validaData(String dataExame) throws ParseException, DateTimeParseException {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dtaExame;
		dtaExame = LocalDate.parse(dataExame, date);
		return dtaExame;
	}

	public LocalTime validaHorario(String horarioExame) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime hrExm;
		hrExm = LocalTime.parse(horarioExame, timeFormatter);
		return hrExm;
	}

	public int getIdExame() {
		return IdExame;
	}

	public void setIdExame(int idExame) {
		IdExame = idExame;
	}

	public String getNomeExame() {
		return nomeExame;
	}

	public void setNomeExame(String nomeExame) {
		this.nomeExame = nomeExame;
	}

	public String getDataExame() {
		return dataExame;
	}

	public void setDataExame(String dataExame) {
		this.dataExame = dataExame;
	}

	public String getHorarioExame() {
		return horarioExame;
	}

	public void setHorarioExame(String horarioExame) {
		this.horarioExame = horarioExame;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

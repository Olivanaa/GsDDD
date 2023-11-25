package br.com.fiap.gs.models;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.com.fiap.gs.enums.TipoAlimentacaoEnum;

public class Alimentacao {

	private int idAlim;
	private TipoAlimentacaoEnum tipo;
	private String alimentosConsumidos;
	private String dtaAlim;
	private Usuario usuario;

	public LocalDate validaData(String dtaAlim) throws ParseException, DateTimeParseException {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dataAlim;
		dataAlim = LocalDate.parse(dtaAlim, date);
		return dataAlim;
	}
	

	public int getIdAlim() {
		return idAlim;
	}

	public void setIdAlim(int idAlim) {
		this.idAlim = idAlim;
	}

	public TipoAlimentacaoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoAlimentacaoEnum tipo) {
		this.tipo = tipo;
	}

	public String getAlimentosConsumidos() {
		return alimentosConsumidos;
	}

	public void setAlimentosConsumidos(String alimentosConsumidos) {
		this.alimentosConsumidos = alimentosConsumidos;
	}

	public String getDtaAlim() {
		return dtaAlim;
	}

	public void setDtaAlim(String dtaAlim) {
		this.dtaAlim = dtaAlim;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

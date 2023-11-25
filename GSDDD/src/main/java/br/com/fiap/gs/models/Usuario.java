package br.com.fiap.gs.models;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.mindrot.jbcrypt.BCrypt;

import br.com.fiap.gs.dao.UsuarioDao;

import br.com.fiap.gs.enums.ObjetivoEnum;

public class Usuario {

	private int id;
	private String nome;
	private String cpf;
	private String genero;
	private int idade;
	private String email;
	private String senha;
	private ObjetivoEnum objetivo;
	

	public static String senhaCripto(String senha) {
		return BCrypt.hashpw(senha, BCrypt.gensalt());
	}

	public boolean verificaSenha(String senhaDigitada, String senhaBanco) {
		return BCrypt.checkpw(senhaDigitada, senhaBanco);
	}

	public boolean verificaLogin(String login) {
		UsuarioDao dao = new UsuarioDao();
		return dao.verificaLogin(this.getEmail());
	}

	public boolean validaCPF(String cpf) {
		if (cpf.length() != 11) {
			return false;
		}
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999")) {
			return false;
		}
		int[] digitos = new int[11];
		for (int i = 0; i < 11; i++) {
			digitos[i] = cpf.charAt(i) - '0';
		}
		int soma1 = 0;
		int soma2 = 0;
		int peso1 = 10;
		int peso2 = 11;

		for (int i = 0; i < 9; i++) {
			int num = cpf.charAt(i) - '0';
			soma1 = soma1 + (num * peso1);
			peso1 = peso1 - 1;
		}
		for (int i = 0; i < 10; i++) {
			int num = cpf.charAt(i) - '0';
			soma2 = soma2 + (num * peso2);
			peso2 = peso2 - 1;
		}

		int resto1 = (soma1 % 11);
		int digUm = (resto1 < 2) ? 0 : (11 - resto1);

		int resto2 = soma2 % 11;
		int digDois = (resto2 < 2) ? 0 : (11 - resto2);

		return (digUm == digitos[9] && digDois == digitos[10]);
	}

	public boolean validaEmail(String email) {
		boolean result = true;
		try {
			InternetAddress emailEnd = new InternetAddress(email);
			emailEnd.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}



	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public ObjetivoEnum getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(ObjetivoEnum objetivo) {
		this.objetivo = objetivo;
	}

}

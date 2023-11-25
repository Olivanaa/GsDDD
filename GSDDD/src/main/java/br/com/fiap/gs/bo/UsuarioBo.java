package br.com.fiap.gs.bo;

import java.sql.SQLException;
import java.text.ParseException;

import br.com.fiap.gs.dao.EnderecoDao;
import br.com.fiap.gs.dao.UsuarioDao;
import br.com.fiap.gs.models.Endereco;
import br.com.fiap.gs.models.Usuario;

public class UsuarioBo {

	private UsuarioDao dao;

	public boolean inserir(Usuario user) throws SQLException {
		dao = new UsuarioDao();
		
		if (!user.validaCPF(user.getCpf())) {
			return false;
		}

		if (!user.validaEmail(user.getEmail())) {
			return false;
		}

		if (user.verificaLogin(user.getEmail())) {
			return false;
		}
		
		int id = dao.buscarMaiorId();
		user.setId(id);
		dao.inserir(user);
		return true;
	}

	public boolean validar(String email, String senha) throws SQLException {
		dao = new UsuarioDao();
		return dao.validarUsuario(email, senha);

	}

	public int buscarPorId(String email) throws SQLException {
		dao = new UsuarioDao();
		return dao.buscarIdLogin(email);
	}

	public void alterar(Usuario user) throws SQLException {
		dao = new UsuarioDao();
		dao.buscarIdLogin(user.getEmail());
		dao.alterar(user);

	}

	public Usuario buscar(int id) throws SQLException {
		dao = new UsuarioDao();
		return dao.buscarPorId(id);
	}

}

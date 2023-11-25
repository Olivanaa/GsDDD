package br.com.fiap.gs.bo;

import java.sql.SQLException;
import java.util.List;

import br.com.fiap.gs.dao.AlimentacaoDao;
import br.com.fiap.gs.dao.UsuarioDao;
import br.com.fiap.gs.models.Alimentacao;
import br.com.fiap.gs.models.Usuario;

public class AlimentacaoBo {

	private AlimentacaoDao dao;
	private UsuarioDao uDao;

	public AlimentacaoBo() {
		dao = new AlimentacaoDao();
		uDao = new UsuarioDao();
	}

	public void cadastrarAlim(int idUser, Alimentacao ali) throws SQLException {
		
		Usuario u = uDao.buscarPorId(idUser);
		ali.setUsuario(u);

		int id = dao.buscarMaiorId();
		ali.setIdAlim(id);
		dao.inserir(ali);


	}

	public Alimentacao buscarAli(int id) {
		return dao.buscarPorId(id);
	}

	public void atualizarAli(Alimentacao ali) {
		dao.alterar(ali);
	}

	public void excluirAli(int id) {
		dao.remover(id);
	}

	public List<Alimentacao> buscarTodos(int idU) {
		return dao.buscarPorIdUser(idU);
	}

}

package br.com.fiap.gs.bo;

import java.util.List;

import br.com.fiap.gs.dao.ExameMedicoDao;
import br.com.fiap.gs.dao.UsuarioDao;
import br.com.fiap.gs.models.ExameMedico;
import br.com.fiap.gs.models.Usuario;

public class ExameMedicoBo {

	private ExameMedicoDao dao;
	private UsuarioDao uDao;

	public ExameMedicoBo() {
		dao = new ExameMedicoDao();
		uDao = new UsuarioDao();
	}

	public void cadastrarExm(int idU, ExameMedico exame) {
		try {
			Usuario u = uDao.buscarPorId(idU);
			exame.setUsuario(u);

			exame.setIdExame(dao.buscarMaiorId());
			dao.inserir(exame);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExameMedico buscarExm(int id) {
		return dao.buscarPorId(id);
	}

	public void atualizarExm(ExameMedico exame) {
		dao.alterar(exame);
	}

	public void excluirExm(int id) {
		dao.remover(id);
	}

	public List<ExameMedico> buscarTodos(int idU) {
		return dao.buscarPorIdUser(idU);
	}

}

package br.com.fiap.gs.bo;

import java.util.List;

import br.com.fiap.gs.dao.MetaDao;
import br.com.fiap.gs.dao.UsuarioDao;
import br.com.fiap.gs.models.Meta;
import br.com.fiap.gs.models.Usuario;

public class MetaBo {
	
	private MetaDao dao;
	private UsuarioDao uDao;

	public MetaBo() {
		dao = new MetaDao();
		uDao = new UsuarioDao();
	}

	public void cadastrarMeta(int idU, Meta meta) {
		try {
			Usuario u = uDao.buscarPorId(idU);
			meta.setUsuario(u);

			meta.setIdMeta(dao.buscarMaiorId());
			dao.inserir(meta);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Meta buscarMeta(int id) {
		return dao.buscarPorId(id);
	}

	public void atualizarMeta(Meta meta) {
		dao.alterar(meta);
	}

	public void excluirMeta(int id) {
		dao.remover(id);
	}

	public List<Meta> buscarTodos(int idU) {
		return dao.buscarPorIdUser(idU);
	}

}

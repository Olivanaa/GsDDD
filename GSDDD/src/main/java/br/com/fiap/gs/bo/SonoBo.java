package br.com.fiap.gs.bo;

import java.util.List;

import br.com.fiap.gs.dao.SonoDao;
import br.com.fiap.gs.dao.UsuarioDao;
import br.com.fiap.gs.models.Sono;
import br.com.fiap.gs.models.Usuario;

public class SonoBo {

	private SonoDao dao;
	private UsuarioDao uDao;

	public SonoBo() {
		dao = new SonoDao();
		uDao = new UsuarioDao();
	}

	public void cadastrarSono(int idU, Sono sono) {
		try {
			Usuario u = uDao.buscarPorId(idU);
			sono.setUsuario(u);

			sono.setIdSono(dao.buscarMaiorId());
			dao.inserir(sono);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Sono buscarSono(int id) {
		return dao.buscarPorId(id);
	}

	public void atualizarSono(Sono sono) {
		dao.alterar(sono);
	}

	public void excluirSono(int id) {
		dao.remover(id);
	}

	public List<Sono> buscarTodos(int idU) {
		return dao.buscarPorIdUser(idU);
	}
	
	public double MediaSono(int idU) {
		dao.RegistroSono(idU);
	    List<Sono> registrosSono = dao.RegistroSono(idU);

	    if (registrosSono.isEmpty()) {
	        return 0.0; 
	    }

	    double somaDuracao = 0.0;
	    for (Sono registro : registrosSono) {
	
	        somaDuracao += registro.getDuracao();
	    }

	    return somaDuracao / registrosSono.size();
		
	}

}

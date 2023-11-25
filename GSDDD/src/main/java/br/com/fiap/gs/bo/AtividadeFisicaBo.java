package br.com.fiap.gs.bo;

import java.util.List;

import br.com.fiap.gs.dao.AtividaFisicaDao;
import br.com.fiap.gs.dao.UsuarioDao;
import br.com.fiap.gs.models.AtividadeFisica;
import br.com.fiap.gs.models.Usuario;

public class AtividadeFisicaBo {

	private AtividaFisicaDao dao;
	private UsuarioDao uDao;

	public AtividadeFisicaBo() {
		dao = new AtividaFisicaDao();
		uDao = new UsuarioDao();
	}

	public void cadastrarAtv(int idU, AtividadeFisica atvF) {
		try {
			Usuario u = uDao.buscarPorId(idU);
			atvF.setUsuario(u);

			atvF.setIdAF(dao.buscarMaiorId());
			dao.inserir(atvF);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AtividadeFisica buscarAtv(int id) {
		return dao.buscarPorId(id);
	}

	public void atualizarAtv(AtividadeFisica atvF) {
		dao.alterar(atvF);
	}

	public void excluirAtv(int id) {
		dao.remover(id);
	}

	public List<AtividadeFisica> buscarTodos(int idU) {
		return dao.buscarPorIdUser(idU);
	}
	
	public double TotalAtv(int idU) {
		dao.RegistroAtividade(idU);
	    List<AtividadeFisica> registrosAtv = dao.RegistroAtividade(idU);

	    if (registrosAtv.isEmpty()) {
	        return 0.0; 
	    }

	    double somaDuracao = 0.0;
	    for (AtividadeFisica registro : registrosAtv) {
	
	        somaDuracao += registro.getDuracao();
	    }

	    return somaDuracao;
		
	}

}

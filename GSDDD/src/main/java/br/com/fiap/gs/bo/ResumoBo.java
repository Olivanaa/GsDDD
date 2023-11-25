package br.com.fiap.gs.bo;

import java.sql.SQLException;
import java.util.List;

import br.com.fiap.gs.dao.AlimentacaoDao;
import br.com.fiap.gs.dao.AtividaFisicaDao;
import br.com.fiap.gs.dao.ExameMedicoDao;
import br.com.fiap.gs.dao.MetaDao;
import br.com.fiap.gs.dao.SonoDao;
import br.com.fiap.gs.dao.UsuarioDao;
import br.com.fiap.gs.enums.ObjetivoEnum;
import br.com.fiap.gs.models.Alimentacao;
import br.com.fiap.gs.models.AtividadeFisica;
import br.com.fiap.gs.models.ExameMedico;
import br.com.fiap.gs.models.Meta;
import br.com.fiap.gs.models.Sono;
import br.com.fiap.gs.models.Usuario;

public class ResumoBo {
	
	private SonoDao sonoDao;
	private AlimentacaoDao aliDao;
	private AtividaFisicaDao atiDao;
	private ExameMedicoDao exaDao;
	private MetaDao metaDao;
	private UsuarioDao uDao;

	public ResumoBo() {
		sonoDao = new SonoDao();
		aliDao = new AlimentacaoDao();
		atiDao = new AtividaFisicaDao();
		exaDao = new ExameMedicoDao();
		metaDao = new MetaDao();
		uDao = new UsuarioDao();
	}



	public Sono buscarSono(int idUser) {
		try {
			sonoDao.buscarUltimoRegistro(idUser);
			return sonoDao.buscarUltimoRegistro(idUser);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<ExameMedico> buscarExame(int idUser) {
		return exaDao.buscarPorIdUser(idUser);
	}
	
	public List<Meta> buscarMeta(int idUser) {
		return metaDao.buscarPorIdUser(idUser);
	}
	
	public AtividadeFisica buscarAtividaFisica(int idUser) {
		try {
			atiDao.buscarUltimoRegistro(idUser);
			return atiDao.buscarUltimoRegistro(idUser);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ObjetivoEnum buscarObejtivo(int idUser) {
	
		return uDao.buscarObjetivoUsuario(idUser);
	
	}
	

	

}

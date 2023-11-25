package br.com.fiap.gs.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.fiap.gs.bo.ResumoBo;
import br.com.fiap.gs.enums.ObjetivoEnum;
import br.com.fiap.gs.models.Alimentacao;
import br.com.fiap.gs.models.AtividadeFisica;
import br.com.fiap.gs.models.ExameMedico;
import br.com.fiap.gs.models.Meta;
import br.com.fiap.gs.models.Sono;
import br.com.fiap.gs.models.Usuario;

@Path("/resumo")
public class ResumoResource {
	
	private ResumoBo resBo;

	public ResumoResource() {
		resBo = new ResumoBo();
	}
	
	@GET
	@Path("sono")
	@Produces(MediaType.APPLICATION_JSON)
	public Sono buscarSono(@QueryParam("id") int idUser) {
		return resBo.buscarSono(idUser);
	}
	
	@GET
	@Path("meta")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Meta> buscarMeta(@QueryParam("id") int idUser) {
		return resBo.buscarMeta(idUser);
	}

	@GET
	@Path("atividade")
	@Produces(MediaType.APPLICATION_JSON)
	public AtividadeFisica buscarAtividade(@QueryParam("id") int idUser) {
		return resBo.buscarAtividaFisica(idUser);
	}
	@GET
	@Path("exame")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ExameMedico> buscarExame(@QueryParam("id") int idUser) {
		return resBo.buscarExame(idUser);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ObjetivoEnum buscarObjetivo(@QueryParam("id") int idUser) {
		return resBo.buscarObejtivo(idUser);
	}

	
}

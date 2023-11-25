package br.com.fiap.gs.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.fiap.gs.bo.ExameMedicoBo;
import br.com.fiap.gs.models.ExameMedico;

@Path("/examemedico")
public class ExameMedicoResource {

	private ExameMedicoBo exmBo;

	public ExameMedicoResource() {
		exmBo = new ExameMedicoBo();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarAli(@QueryParam("id") int idUser, ExameMedico exm, @Context UriInfo uriInfo) {
		try {
			exmBo.cadastrarExm(idUser, exm);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(exm.getIdExame()));
			return Response.created(builder.build()).entity("{\"IdExame\": " + exm.getIdExame() + "}").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar").build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ExameMedico buscarPorId(@PathParam("id") int id) {
		return exmBo.buscarExm(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ExameMedico> buscar(@QueryParam("id") int idUser) {
		return exmBo.buscarTodos(idUser);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(ExameMedico exm, @PathParam("id") int id) {
		exm.setIdExame(id);
		exmBo.atualizarExm(exm);
		return Response.ok().build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remover(@QueryParam("id") int id) {
		exmBo.excluirExm(id);
		return Response.ok().build();

	}
}

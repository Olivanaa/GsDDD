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

import br.com.fiap.gs.bo.AtividadeFisicaBo;
import br.com.fiap.gs.models.AtividadeFisica;

@Path("/atividadefisica")
public class AtividadeFisicaResource {

	private AtividadeFisicaBo atvFBo;

	public AtividadeFisicaResource() {
		atvFBo = new AtividadeFisicaBo();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarAli(@QueryParam("id") int idUser, AtividadeFisica atvF, @Context UriInfo uriInfo) {
		try {
			atvFBo.cadastrarAtv(idUser, atvF);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(atvF.getIdAF()));
			return Response.created(builder.build()).entity("{\"IdAtividade\": " + atvF.getIdAF() + "}").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar").build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AtividadeFisica buscarPorId(@PathParam("id") int id) {
		return atvFBo.buscarAtv(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AtividadeFisica> buscar(@QueryParam("id") int idUser) {
		return atvFBo.buscarTodos(idUser);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(AtividadeFisica atvF, @PathParam("id") int id) {
		atvF.setIdAF(id);
		atvFBo.atualizarAtv(atvF);
		return Response.ok().build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remover(@QueryParam("id") int id) {
		atvFBo.excluirAtv(id);
		return Response.ok().build();

	}
	
	@GET
	@Path("/estatisticas")
	@Produces(MediaType.APPLICATION_JSON)
	public double somaRegistros(@QueryParam("id") int idUser) {
		return atvFBo.TotalAtv(idUser);
	}

}

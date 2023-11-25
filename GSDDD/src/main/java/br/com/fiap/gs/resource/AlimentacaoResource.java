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

import br.com.fiap.gs.bo.AlimentacaoBo;
import br.com.fiap.gs.models.Alimentacao;

@Path("/alimentacao")
public class AlimentacaoResource {

	private AlimentacaoBo aliBo;

	public AlimentacaoResource() {
		aliBo = new AlimentacaoBo();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarAli(@QueryParam("id") int idUser, Alimentacao ali, @Context UriInfo uriInfo) {
		try {
			aliBo.cadastrarAlim(idUser, ali);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(ali.getIdAlim()));
			return Response.created(builder.build()).entity("{\"IdAlimentacao\": " + ali.getIdAlim() + "}").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar").build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Alimentacao buscarPorId(@PathParam("id") int id) {
		return aliBo.buscarAli(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Alimentacao> buscar(@QueryParam("id") int idUser) {
		return aliBo.buscarTodos(idUser);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Alimentacao ali, @PathParam("id") int id) {
		ali.setIdAlim(id);
		aliBo.atualizarAli(ali);
		return Response.ok().build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remover(@QueryParam("id") int id) {
		aliBo.excluirAli(id);
		return Response.ok().build();

	}

}

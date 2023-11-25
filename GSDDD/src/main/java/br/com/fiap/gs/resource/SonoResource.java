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

import br.com.fiap.gs.bo.SonoBo;
import br.com.fiap.gs.models.Sono;

@Path("/sono")
public class SonoResource {

	private SonoBo sonoBo;

	public SonoResource() {
		sonoBo = new SonoBo();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrarAli(@QueryParam("id") int idUser, Sono sono, @Context UriInfo uriInfo) {
		try {
			sonoBo.cadastrarSono(idUser, sono);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(sono.getIdSono()));
			return Response.created(builder.build()).entity(sono.getIdSono()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar").build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Sono buscarPorId(@PathParam("id") int id) {
		return sonoBo.buscarSono(id);
	}

	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sono> buscar(@QueryParam("id") int idUser) {
		return sonoBo.buscarTodos(idUser);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Sono sono, @PathParam("id") int id) {
		sono.setIdSono(id);
		sonoBo.atualizarSono(sono);
		return Response.ok().build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remover(@QueryParam("id") int id) {
		sonoBo.excluirSono(id);
		return Response.ok().build();

	}
	
	@GET
	@Path("/registros/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public double mediaRegistros(@QueryParam("id") int idUser) {
		return sonoBo.MediaSono(idUser);
	}

}

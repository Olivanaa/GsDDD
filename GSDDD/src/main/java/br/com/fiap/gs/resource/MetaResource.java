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

import br.com.fiap.gs.bo.MetaBo;
import br.com.fiap.gs.models.Meta;

@Path("/metas")
public class MetaResource {

		private MetaBo metaBo;

		public MetaResource() {
			metaBo = new MetaBo();
		}

		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		public Response cadastrarMeta(@QueryParam("id") int idUser, Meta meta, @Context UriInfo uriInfo) {
			try {
				metaBo.cadastrarMeta(idUser, meta);
				UriBuilder builder = uriInfo.getAbsolutePathBuilder();
				builder.path(Integer.toString(meta.getIdMeta()));
				return Response.created(builder.build()).entity("{\"IdMeta\": " + meta.getIdMeta() + "}").build();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar").build();
			}
		}

		@GET
		@Path("/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Meta buscarPorId(@PathParam("id") int id) {
			return metaBo.buscarMeta(id);
		}

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<Meta> buscar(@QueryParam("id") int idUser) {
			return metaBo.buscarTodos(idUser);
		}

		@PUT
		@Path("/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response atualizar(Meta meta, @PathParam("id") int id) {
			meta.setIdMeta(id);
			metaBo.atualizarMeta(meta);
			return Response.ok().build();
		}

		@DELETE
		@Consumes(MediaType.APPLICATION_JSON)
		public Response remover(@QueryParam("id") int id) {
			metaBo.excluirMeta(id);
			return Response.ok().build();

		}
		

}

package br.com.fiap.gs.resource;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.undo.UndoableEditSupport;
import javax.ws.rs.Consumes;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.gs.bo.UsuarioBo;
import br.com.fiap.gs.models.Endereco;
import br.com.fiap.gs.models.Usuario;
import br.com.fiap.gs.services.ViaCepService;

@Path("/conta")
public class UsuarioResource {

	private UsuarioBo userBo;

	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrar(Usuario user, @Context UriInfo uriInfo) {
		try {

			userBo = new UsuarioBo();

			if (userBo.inserir(user)) {
				UriBuilder builder = uriInfo.getAbsolutePathBuilder();
				builder.path(Integer.toString((user.getId())));
				return Response.created(builder.build()).build();
			} else {
				if (!user.validaCPF(user.getCpf())) {
					return Response.status(Response.Status.BAD_REQUEST).entity("CPF inválido").build();
				} else if (!user.validaEmail(user.getEmail())) {
					return Response.status(Response.Status.BAD_REQUEST).entity("Email inválido").build();
				} else {
					return Response.status(Response.Status.BAD_REQUEST).entity("Login já existente").build();
				}
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.serverError().entity("Erro interno no servidor").build();
		}catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Erro interno no servidor").build();
		}
	}


	@GET
	@Path("/login")
	public Response validarUsuario(@QueryParam("email") String email, @QueryParam("senha") String senha)
			throws SQLException {

		userBo = new UsuarioBo();
		Boolean resp = userBo.validar(email, senha);

		if (resp) {
			int idUsuario = userBo.buscarPorId(email);
			return Response.status(Response.Status.OK).entity("{\"IdUsuario\": " + idUsuario + "}").build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário inválido").build();
		}
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Usuario user, @PathParam("id") int id) {
		user.setId(id);
		userBo = new UsuarioBo();
		try {
			userBo.alterar(user);
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	
		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario buscarPorId(@QueryParam("id") int id) throws SQLException {
		userBo = new UsuarioBo();
		return userBo.buscar(id);
	}
}

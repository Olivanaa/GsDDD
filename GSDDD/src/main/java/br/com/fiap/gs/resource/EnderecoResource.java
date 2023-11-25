package br.com.fiap.gs.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fiap.gs.models.Endereco;
import br.com.fiap.gs.services.ViaCepService;

@Path("/criarconta")
public class EnderecoResource {

	@GET
	@Path("/buscarcep/{cep}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarCep(@PathParam("cep") String cep) {
		ViaCepService viaCepService = new ViaCepService();
		Endereco endereco = viaCepService.buscaEndereco(cep);
		return Response.ok(endereco).build();
	}

}

package br.com.fiap.gs.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import br.com.fiap.gs.models.Endereco;

public class ViaCepService {

	public Endereco buscaEndereco(String cep) {
		try {
			String endereco = "https://viacep.com.br/ws/" + cep + "/json";
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() >= 400) {

				System.out.println("CEP: " + cep);
				throw new RuntimeException("ViaCep API returned an error: " + response.statusCode());
			}

			// Parse the JSON response if the status code is not an error
			return new Gson().fromJson(response.body(), Endereco.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

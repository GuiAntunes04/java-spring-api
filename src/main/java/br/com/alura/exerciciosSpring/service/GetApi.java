package br.com.alura.exerciciosSpring.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class GetApi {
	
	public String getData(String address) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
		      .uri(URI.create(address))
		      .build();
		
		HttpResponse<String> response = client
				  .send(request, BodyHandlers.ofString());
		
		String json = response.body();
		return json;
	}
}

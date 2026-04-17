package br.com.alura.exerciciosSpring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.exerciciosSpring.model.SeriesData;
import br.com.alura.exerciciosSpring.service.ConvertData;
import br.com.alura.exerciciosSpring.service.GetApi;

@SpringBootApplication
public class ExerciciosSpringApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ExerciciosSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new GetApi();
		var json = consumoApi.getData("https://www.omdbapi.com/?t=the+good+doctor&apikey=2b22b79");
		System.out.println(json);
		ConvertData converter = new ConvertData();
		SeriesData data = converter.getData(json, SeriesData.class);
		System.out.println(data);
		
	}

}

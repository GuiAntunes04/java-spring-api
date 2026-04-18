package br.com.alura.exerciciosSpring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.exerciciosSpring.model.SeasonData;
import br.com.alura.exerciciosSpring.model.SerieData;
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
		
		System.out.println("Dados da serie:");
		var serie = consumoApi.getData("https://www.omdbapi.com/?t=the+good+doctor&apikey=2b22b79");
		ConvertData converter = new ConvertData();
		SerieData toSeries = converter.getData(serie, SerieData.class);
		System.out.println(toSeries);
		
		System.out.println();
		
		System.out.println("Dados da temp:");
		for (int i = 1; i <= toSeries.totalSeasons() ; i++) {
			var season = consumoApi.getData("https://www.omdbapi.com/?t=the+good+doctor&season=" + i + "&apikey=2b22b79");
			SeasonData toSeason = converter.getData(season, SeasonData.class);
			System.out.println(toSeason);
		}
		
		
	}

}

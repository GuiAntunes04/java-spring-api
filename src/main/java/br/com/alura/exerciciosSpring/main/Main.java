package br.com.alura.exerciciosSpring.main;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.com.alura.exerciciosSpring.model.EpisodeData;
import br.com.alura.exerciciosSpring.model.SeasonData;
import br.com.alura.exerciciosSpring.model.SerieData;
import br.com.alura.exerciciosSpring.service.ConvertData;
import br.com.alura.exerciciosSpring.service.GetApi;

public class Main {
	
	private Scanner sc = new Scanner(System.in);
	
	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=2b22b79";
	private GetApi requestApi = new GetApi();
	private ConvertData converter = new ConvertData();
	private List<SeasonData> season = new ArrayList<>();
	
	public void displayMenu() throws IOException, InterruptedException {
		
		System.out.print("Digite o nome da série para busca: ");
		var serieName = sc.nextLine();
		
		//Convert Json into Obj SerieData 
		String encodedName = URLEncoder.encode(serieName, StandardCharsets.UTF_8);
		String url = ENDERECO + encodedName + API_KEY;
		var json = requestApi.getData(url);
		SerieData toSeries = converter.getData(json, SerieData.class);
		
		
		//Convert Json into Obj SeasonData 
		IntStream.rangeClosed(1, toSeries.totalSeasons())
	    .mapToObj(i -> {
	        try {
	            return requestApi.getData(
	                ENDERECO + serieName.replace(" ", "+") + "&season=" + i + API_KEY
	            );
	        } catch (IOException | InterruptedException e) {
	            throw new RuntimeException(e);
	        }
	    })
	    .map(data -> converter.getData(data, SeasonData.class))
	    .forEach(season::add);
		
		System.out.println("\nEpisodes: ");
		
		List<EpisodeData> episode = season.stream()
				.flatMap(s -> s.episodes().stream()
						.map(e -> new EpisodeData(e , s.numSeason())))
				.collect(Collectors.toList());
		
		episode.forEach(System.out::println);
		
		System.out.println("\n5 melhores episódios: ");
		
		episode.stream()
		.filter(e -> e.getRating() != 0.0)
		.sorted(Comparator.comparingDouble(EpisodeData::getRating).reversed())
		.limit(5)
		.forEach(e -> System.out.println(e));

		
		System.out.println("\n5 piores episódios: ");
		
		episode.stream()
		.filter(e -> e.getRating() != 0.0)
		.sorted(Comparator.comparingDouble(EpisodeData::getRating))
		.limit(5)
		.forEach(e -> System.out.println(e));
		
		System.out.println("\nAvaliação média de cada temporada: ");
		
		Map<Integer, Double> seasonRating = episode.stream()
				.filter(e -> e.getRating() != 0.0)
				.collect(Collectors.groupingBy(EpisodeData::getSeason, Collectors.averagingDouble(EpisodeData::getRating)));
		System.out.println(seasonRating);
	
	}
	
		
}

package br.com.alura.exerciciosSpring.main;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.exerciciosSpring.dto.SerieDto;
import br.com.alura.exerciciosSpring.model.Category;
import br.com.alura.exerciciosSpring.model.EpisodeData;
import br.com.alura.exerciciosSpring.model.SeasonData;
import br.com.alura.exerciciosSpring.model.SerieData;
import br.com.alura.exerciciosSpring.repository.SerieRepository;
import br.com.alura.exerciciosSpring.service.ConvertData;
import br.com.alura.exerciciosSpring.service.GetApi;

public class Main {
	
	private Scanner sc = new Scanner(System.in);

	private int option;
	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=2b22b79";
	private String encodedName;
	private GetApi requestApi = new GetApi();
	private ConvertData converter = new ConvertData();
	private List<EpisodeData> episode = new ArrayList<>();
	private List<SerieData> listSeries = new ArrayList<>();
	private List<SerieData> seriesSearched = new ArrayList<>();;
	private SeasonData seasonData;
	private SerieData serie;
	private SerieDto toSeries;
	private String serieName;
	private String actorName;
	private Double rating;
	private String genre;
	
	
	private SerieRepository repository;
	
	public Main(SerieRepository repository) {
		this.repository = repository;
	}


	public void displayMenu() throws IOException, InterruptedException {
		option = -1;
		while(option != 0) {
			
			var menu = 
				"""
				Escolha uma opção:
				
				1 - Buscar séries
				2 - Buscar episódios por série
				3 - Listar séries buscadas
				4 - Buscar série por título
				5 - Buscar série pelo ator
				6 - Buscar série pela avaliação
				7 - Buscar série pela categoria
				
				0 - Sair
				
				Digite: 
				""";
			
			System.out.println(menu);
			option = sc.nextInt();
			sc.nextLine();
			
			switch (option) {
			case 1:
				findSeries();
				break;
			case 2:
				findEpisodes();
				break;
			case 3:
				listSeries();
				break;
			case 4:
				findByTitles();
				break;
			case 5:
				findByActors();
				break;
			case 6:
				findByRating();
				break;
			case 7:
				findByGenre();
				break;
			case 0:
				System.out.println("Saindo...");
				sc.close();
				break;
			default:
				System.out.println("Opção inválida.");
			}
			
		}
	}
		
		
	private void findSeries() throws IOException, InterruptedException {
		
		toSeries = getSerieData();
        serie = new SerieData(toSeries);
        //dadosSeries.add(dados);
        repository.save(serie);
        System.out.println(serie);
    }

    private SerieDto getSerieData() throws IOException, InterruptedException {
        System.out.println("\nDigite o nome da série para busca");
        serieName = sc.nextLine();
        encodedName = URLEncoder.encode(serieName, StandardCharsets.UTF_8);
        var json = requestApi.getData(ENDERECO + encodedName + API_KEY);
        toSeries = converter.getData(json, SerieDto.class);
        return toSeries;
    }
		
	private void findEpisodes() throws IOException, InterruptedException {
		listSeries();
		System.out.print("\nDigite uma série presente no catálogo: ");
		serieName = sc.nextLine();
		
		Optional<SerieData> serieData = repository.findByTitleContainingIgnoreCase(serieName);
		
		if(serieData.isPresent()) {
			SerieData seriesFounded = serieData.get();
			List<SeasonData> season =new ArrayList<>();
			encodedName = URLEncoder.encode(seriesFounded.getTitle(), StandardCharsets.UTF_8);
			for (int i = 1 ; i <= seriesFounded.getTotalSeasons() ; i++) {
				var json = requestApi.getData(ENDERECO + encodedName + "&season=" + i + API_KEY);
				seasonData = converter.getData(json, SeasonData.class);
				season.add(seasonData);
						
			}
			season.forEach(System.out::println);
			
			episode = season.stream()
					.flatMap(s -> s.episodes().stream()
							.map(e -> new EpisodeData(e, s.numSeason())))
					.collect(Collectors.toList());
			seriesFounded.setEpisodes(episode);
			repository.save(seriesFounded);
		}else {
			System.out.println("404 - Not Found");
		
		}
	}
	
	private void listSeries() {
		listSeries = repository.findAll();
		listSeries.stream()
					.sorted(Comparator.comparing(SerieData::getGenre))
					.forEach(System.out::println);
		System.out.println("***************************");
	}
	
	private void findByTitles() {
		System.out.print("\nDigite uma série presente no catálogo: ");
		serieName = sc.nextLine();
		Optional<SerieData>seriesSearched = repository.findByTitleContainingIgnoreCase(serieName);
		
		if (seriesSearched.isPresent()) {
			 System.out.println("Dados da série: " + seriesSearched.get());
		}
		else {
			System.out.println("Série não encontrada!");
		}
	}
	
	private void findByActors() {
		System.out.print("\nDigite o nome de um ator: ");
		actorName = sc.nextLine();
		seriesSearched = repository.findByActorsContainingIgnoreCase(actorName);
		
		if (!seriesSearched.isEmpty()) {
			System.out.print("Séries encontradas: ");
			seriesSearched.forEach(System.out::println);
		}
		else {
			System.out.println("Ator não encontrado!");
		}
	}	
	
	private void findByRating(){
		System.out.print("\nDigite a avaliação mínima desejada: ");
		rating = sc.nextDouble();
		seriesSearched = repository.findByRatingGreaterThanEqual(rating);
		
		if (!seriesSearched.isEmpty()) {
			System.out.println("Séries encontradas: ");
			seriesSearched.forEach(System.out::println);
		}
		else {
			System.out.println("Nenhuma série encontrada!");
		}
	}	
	
	private void findByGenre() {
		System.out.print("\nDigite o gênero desejado: ");
		genre = sc.nextLine();
		
		Category category = Category.fromPortuguese(genre);
		seriesSearched = repository.findByGenre(category);
		System.out.println("Séries da categoria " + genre + ":");
		seriesSearched.forEach(System.out::println);
		
	}
}

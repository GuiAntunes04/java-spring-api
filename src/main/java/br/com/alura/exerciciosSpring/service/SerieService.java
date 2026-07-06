package br.com.alura.exerciciosSpring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.exerciciosSpring.dto.EpisodeResponseDTO;
import br.com.alura.exerciciosSpring.dto.SerieResponseDTO;
import br.com.alura.exerciciosSpring.model.Category;
import br.com.alura.exerciciosSpring.model.SerieData;
import br.com.alura.exerciciosSpring.repository.SerieRepository;

@Service
public class SerieService {
	
	@Autowired
	private SerieRepository repository;
	
	public List<SerieResponseDTO> getAllSeries() {
		
		return convertData(repository.findAll());
	}

	public List<SerieResponseDTO> getTop5Series() {
		return convertData(repository.findTop5ByOrderByRatingDesc());
	}

	public List<SerieResponseDTO> getNewReleases() {
		return convertData(repository.newReleases());
	}
	
	private List<SerieResponseDTO> convertData(List<SerieData> series){
			return series.stream()
					.map(s -> new SerieResponseDTO(s.getId(), s.getTitle(),s.getTotalSeasons(),s.getRating(), s.getGenre(), s.getActors(), s.getPoster(), s.getPlot()))
					.collect(Collectors.toList());
		}

	public SerieResponseDTO getById(Long id) {
		Optional<SerieData> serie = repository.findById(id);
		
		if (serie.isPresent()){
			SerieData s = serie.get();
			return new SerieResponseDTO(s.getId(), s.getTitle(),s.getTotalSeasons(),s.getRating(), s.getGenre(), s.getActors(), s.getPoster(), s.getPlot());
		}
		return null;
	}

	public List<EpisodeResponseDTO> getAllSeasons(Long id) {
		Optional<SerieData> serie = repository.findById(id);
		
		if (serie.isPresent()){
			SerieData s = serie.get();
			return s.getEpisodes().stream()
					.map(e -> new EpisodeResponseDTO(e.getSeason(), e.getEpisode(), e.getTitle()))
					.collect(Collectors.toList());
		}
		return null;
	}

	public List<EpisodeResponseDTO> getSeasonsByNumber(Long id, Long number) {
		return repository.getEpisodesBySeason(id, number)
				.stream()
				.map(e -> new EpisodeResponseDTO(e.getSeason(), e.getEpisode(), e.getTitle()))
				.collect(Collectors.toList());
	}

	public List<SerieResponseDTO> getSeriesByCategory(String categoryName) {
		Category category = Category.fromPortuguese(categoryName);
		return convertData(repository.findByGenre(category));
	}

}

package br.com.alura.exerciciosSpring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.exerciciosSpring.dto.EpisodeResponseDTO;
import br.com.alura.exerciciosSpring.dto.SerieResponseDTO;
import br.com.alura.exerciciosSpring.service.SerieService;

@RestController
@RequestMapping("/series")
public class SeriesController {
	
	@Autowired
	private SerieService service;
	
	@GetMapping
	public List<SerieResponseDTO> getAllSeries() {
		return service.getAllSeries();
	}
	
	@GetMapping("top5")
	public List<SerieResponseDTO> getTop5Series() {
		return service.getTop5Series();
	}
	
	@GetMapping("releases")
	public List<SerieResponseDTO> getNewReleases() {
		return service.getNewReleases();
	}
	
	@GetMapping("/{id}")
	public SerieResponseDTO getById(@PathVariable Long id) {
		return service.getById(id);
	}
	
	@GetMapping("/{id}/seasons/all")
	public List<EpisodeResponseDTO> getAllSeasons(@PathVariable Long id) {
		return service.getAllSeasons(id);
	}
	
	@GetMapping("/{id}/seasons/{number}")
	public List<EpisodeResponseDTO> getSeasonsByNumber(@PathVariable Long id, @PathVariable Long number) {
		return service.getSeasonsByNumber(id, number);
	}
	
}

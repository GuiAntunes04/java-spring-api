package br.com.alura.exerciciosSpring.dto;

import br.com.alura.exerciciosSpring.model.Category;

public record SerieResponseDTO(
		Long id,
		String title,	
		Integer totalSeasons,
		Double rating,		
		Category genre,	
		String actors,	
		String poster,	
		String plot) {
		}


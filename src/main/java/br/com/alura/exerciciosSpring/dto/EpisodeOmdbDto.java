package br.com.alura.exerciciosSpring.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeOmdbDto(
		@JsonAlias("Title") String titleEp,
		@JsonAlias("Episode") String numEp,
		@JsonAlias("imdbRating") String ratingEp,
		@JsonAlias("Released") String releasedEp
		) {

}

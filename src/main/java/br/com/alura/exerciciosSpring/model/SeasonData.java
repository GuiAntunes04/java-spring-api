package br.com.alura.exerciciosSpring.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.alura.exerciciosSpring.dto.EpisodeOmdbDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(@JsonAlias("Season") Integer numSeason,
						@JsonAlias("Episodes") List<EpisodeOmdbDto> episodes) {

}

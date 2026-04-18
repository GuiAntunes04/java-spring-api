package br.com.alura.exerciciosSpring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData (@JsonAlias("Title") String titleEpisode,
							@JsonAlias("Episode") Integer numEpisode,
							@JsonAlias("Released") String released) {

}

package br.com.alura.exerciciosSpring.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(@JsonAlias("Season") Integer numSeason,
						@JsonAlias("Episodes") List<EpisodeData> episodes) {

}

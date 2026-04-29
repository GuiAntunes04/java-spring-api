package br.com.alura.exerciciosSpring.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import br.com.alura.exerciciosSpring.dto.EpisodeDto;

public class EpisodeData {
	
	private String title;
    private Integer episode;
    private Double rating;
    private LocalDate released;
    private Integer season;
    
	public EpisodeData(EpisodeDto dto, Integer season) {
		
		this.season = season;
		this.title = dto.titleEp();
		
		try {
			this.episode = Integer.valueOf(dto.numEp());
		}
		catch(NumberFormatException e){
			this.episode = null;
		}
		
		try {
			this.rating = Double.valueOf(dto.ratingEp());
		}
		catch(NumberFormatException e){
			this.rating = 0.0;
		}
		
		try {
			this.released = LocalDate.parse(dto.releasedEp());
		}
		catch(DateTimeParseException e){
			this.rating = null;
		}
		
		
		
	}
	public String getTitle() {
		return title;
	}
	public Integer getEpisode() {
		return episode;
	}
	public Double getRating() {
		return rating;
	}
	public LocalDate getReleased() {
		return released;
	}
	public Integer getSeason() {
		return season;
	}
	@Override
	public String toString() {
		return "Title=" + title +
				", Episode=" + episode + 
				", Rating=" + rating + 
				", Released=" + released + 
				", Season=" + season;
	}

	    
	}
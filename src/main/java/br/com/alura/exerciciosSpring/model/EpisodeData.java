package br.com.alura.exerciciosSpring.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import br.com.alura.exerciciosSpring.dto.EpisodeOmdbDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "episodes")
public class EpisodeData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
    private Integer episode;
    private Double rating;
    private LocalDate released;
    private Integer season;
    
    @ManyToOne
    private SerieData serie;
    
    public EpisodeData() {}
    
	public EpisodeData(EpisodeOmdbDto episodeDto, Integer season) {
		
		this.season = season;
		this.title = episodeDto.titleEp();
		
		try {
			this.episode = Integer.valueOf(episodeDto.numEp());
		}
		catch(NumberFormatException e){
			this.episode = null;
		}
		
		try {
			this.rating = Double.valueOf(episodeDto.ratingEp());
		}
		catch(NumberFormatException e){
			this.rating = 0.0;
		}
		
		try {
			this.released = LocalDate.parse(episodeDto.releasedEp());
		}
		catch(DateTimeParseException e){
			this.rating = null;
		}
		
		
		
	}
	public Long getId() {
		return id;
	}
	public SerieData getSerie() {
		return serie;
	}
	public void setSerie(SerieData serie) {
		this.serie = serie;
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
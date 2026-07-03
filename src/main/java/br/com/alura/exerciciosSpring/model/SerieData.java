package br.com.alura.exerciciosSpring.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import br.com.alura.exerciciosSpring.dto.SerieOmdbDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table (name = "series")
public class SerieData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (unique = true)
	private String title;
	
	private Integer totalSeasons;
	
	private Double rating;
	
	@Enumerated (EnumType.STRING)
	private Category genre;
	
	private String actors;
	
	private String poster;
	
	private String plot;
	
	@OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<EpisodeData> episodes = new ArrayList<>();
	
	public SerieData() {}
	
	public SerieData(SerieOmdbDto serieDto) throws IOException, InterruptedException {
		this.title = serieDto.title();
		this.totalSeasons = serieDto.totalSeasons();
		this.rating = OptionalDouble.of(Double.valueOf(serieDto.rating())).orElse(0);
		this.genre = Category.fromString(serieDto.genre().split(",")[0].trim());
		this.actors = serieDto.actors();
		this.poster = serieDto.poster();
		this.plot = serieDto.plot();
	}
	
	
	
	public Long getId() {
		return id;
	}



	public String getTitle() {
		return title;
	}

	public Integer getTotalSeasons() {
		return totalSeasons;
	}

	public Double getRating() {
		return rating;
	}

	public Category getGenre() {
		return genre;
	}

	public String getActors() {
		return actors;
	}

	public String getPoster() {
		return poster;
	}

	public String getPlot() {
		return plot;
	}
	
	
	public List<EpisodeData> getEpisodes() {
		return episodes;
	}


	public void setEpisodes(List<EpisodeData> episodes) {
		episodes.forEach(e -> e.setSerie(this));
		this.episodes = episodes;
	}

	@Override
	public String toString() {
		return	 "Title= " + title +
			", TotalSeasons= " + totalSeasons + 
			", Rating= " + rating +
			", Genre= " + genre +
			", Actors= " + actors +
			", Poster= " + poster +
			", Plot= " + plot +
			", Episodes= " + episodes;
	
	}
}



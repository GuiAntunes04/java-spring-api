package br.com.alura.exerciciosSpring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.exerciciosSpring.model.Category;
import br.com.alura.exerciciosSpring.model.SerieData;

public interface SerieRepository extends JpaRepository<SerieData, Long>{
	Optional<SerieData> findByTitleContainingIgnoreCase(String serieName);
	List<SerieData> findByActorsContainingIgnoreCase(String actorName);
	List<SerieData> findByRatingGreaterThanEqual(Double rating);
	List<SerieData> findByGenre(Category category);
	@Query("""
			SELECT s 
			FROM SerieData s 
			WHERE s.rating >= 7.5 
			ORDER BY s.totalSeasons, s.rating DESC LIMIT 10
		""")
	List<SerieData> findShorterTopSeries();
	
}

package br.com.alura.exerciciosSpring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.exerciciosSpring.model.Category;
import br.com.alura.exerciciosSpring.model.SerieData;

public interface SerieRepository extends JpaRepository<SerieData, Long>{
	Optional<SerieData> findByTitleContainingIgnoreCase(String serieName);
	List<SerieData> findByActorsContainingIgnoreCase(String actorName);
	List<SerieData> findByRatingGreaterThanEqual(Double rating);
	List<SerieData> findByGenre(Category category);

	
}

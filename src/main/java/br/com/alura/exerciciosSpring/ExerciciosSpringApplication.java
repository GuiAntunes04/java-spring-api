package br.com.alura.exerciciosSpring;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.exerciciosSpring.main.Main;
import br.com.alura.exerciciosSpring.repository.SerieRepository;

@SpringBootApplication
public class ExerciciosSpringApplication implements CommandLineRunner{
	
	@Autowired
	private SerieRepository repository;

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		SpringApplication.run(ExerciciosSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository);
		main.displayMenu();
	}

}

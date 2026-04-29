package br.com.alura.exerciciosSpring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.exerciciosSpring.main.Main;

@SpringBootApplication
public class ExerciciosSpringApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ExerciciosSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.displayMenu();
	}

}

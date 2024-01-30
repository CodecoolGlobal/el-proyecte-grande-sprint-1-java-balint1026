package com.codecool.puzzleshowdown;

import com.codecool.puzzleshowdown.dao.PuzzleDAO;
import com.codecool.puzzleshowdown.dao.PuzzleDAOJdbc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PuzzleShowdownApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuzzleShowdownApplication.class, args);
	}

	@Bean
	public PuzzleDAO puzzleDAO(){
		return new PuzzleDAOJdbc();
	}

}

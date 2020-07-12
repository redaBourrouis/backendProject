package com.example;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.example.entities.Film;
import com.example.entities.Salle;
import com.example.entities.Ticket;
import com.example.service.IcinemaInitService;
import com.example.web.FilmController;


@SpringBootApplication

public class CinemaAppApplication implements CommandLineRunner{

	@Autowired
    private IcinemaInitService cinemaInitService;
	@Autowired
	private RepositoryRestConfiguration restConfiguration;
	
	public static void main(String[] args) {
		SpringApplication.run(CinemaAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class,Salle.class,Ticket.class);
		cinemaInitService.initville();
		cinemaInitService.initCinema();	
		cinemaInitService.initsalle();		
		cinemaInitService.initplace();		
		cinemaInitService.initSeance();		
		cinemaInitService.initcategorie();		
		cinemaInitService.initfilm();		
	    cinemaInitService.initprojection();
		cinemaInitService.initticket();
		
		
	}

}
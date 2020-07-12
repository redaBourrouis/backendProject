package com.example.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.dao.CategoryRepo;
import com.example.dao.CinemaRepo;
import com.example.dao.FilmRepo;
import com.example.dao.PlaceRepo;
import com.example.dao.ProjectionRepo;
import com.example.dao.SalleRepo;
import com.example.dao.SeanceRepo;
import com.example.dao.TicketRepo;
import com.example.dao.VilleRepo;
import com.example.entities.Categorie;
import com.example.entities.Cinema;
import com.example.entities.Film;
import com.example.entities.Place;
import com.example.entities.Projection;
import com.example.entities.Salle;
import com.example.entities.Seance;
import com.example.entities.Ticket;
import com.example.entities.Ville;



@Transactional @Service 
public class CinemaInitService implements IcinemaInitService{
	@Autowired
private VilleRepo villeRepo;
	@Autowired
	private CinemaRepo cinemaRepo;
	@Autowired
	private SalleRepo salleRepo;
	@Autowired
	private PlaceRepo placeRepo;
	@Autowired
	private SeanceRepo seanceRepo;
	@Autowired
	private FilmRepo filmRepo;
	@Autowired
	private ProjectionRepo projectionRepo;
    @Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private TicketRepo ticketRepo;
	
	@Override
	public void initville() {
		Stream.of("casa","merrakech","rabat","jdida").forEach(nameV->{
			Ville ville =new Ville();
			ville.setName(nameV);
			villeRepo.save(ville);
			
		});
	}

	@Override
	public void initCinema() {
		villeRepo.findAll().forEach(v->{
			Stream.of("megarama","dawaliz","abc","Imax").forEach(nameC->{
				Cinema cinema=new Cinema();
				cinema.setName(nameC);
				cinema.setNombreSalle(20);
				cinema.setVille(v);
				cinemaRepo.save(cinema);
				
		});
		});
	}

	@Override
	public void initsalle() {
		cinemaRepo.findAll().forEach(c->{
			for(int i=0;i<c.getNombreSalle();i++)
			{
				Salle salle=new Salle();
				salle.setName("salle "+(i+1));
				salle.setCinema(c);
				salle.setNombrePlace((int)(2*Math.random()*3*2+10));
				salleRepo.save(salle);
			}
		});
		
	}

	@Override
	public void initSeance() {
		DateFormat date=new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
			Seance seance=new Seance();
			try
			{
		seance.setHeurDebut(date.parse(s));
		seanceRepo.save(seance);
			}
			catch(ParseException e)
			{
			e.printStackTrace();
			}
			
		});
	}

	@Override
	public void initcategorie() {
		
		Stream.of("fiction","horreur","action","amour").forEach(nameC->{
			Categorie c=new Categorie();
			c.setName(nameC);
			categoryRepo.save(c);
			
		});
	}

	@Override
	public void initplace() {
		salleRepo.findAll().forEach(s->{
			
			for(int i=0;i<s.getNombrePlace();i++)
			{
				Place place= new Place();
				place.setNumero(i+1);
				place.setSalle(s);
				placeRepo.save(place);
			}
				
		
		});
	}

	@Override
	public void initfilm() {
		double[] durees=new double[] {1,2,1,4,5,5,2};
		java.util.List<Categorie> categories=categoryRepo.findAll();
		Stream.of("got","breaking bad","vikings","peaky blinders","harry potter","limitless").forEach(f->{
			
			Film film=new Film();
			film.setTitre(f);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(f.replaceAll(" ", "")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepo.save(film);
		});
	}

	@Override
	public void initprojection() {
		double[] prices=new double[] {20,30,50,70,90,120};
		List<Film> films=filmRepo.findAll();
		villeRepo.findAll().forEach(v->{
			v.getCinemas().forEach(c->{
				c.getSalles().forEach(s->{
					int index=new Random().nextInt(films.size());
					Film film;
					film=films.get(index);
					
						seanceRepo.findAll().forEach(seance->{
							Projection p=new Projection();
							p.setDateProjection(new Date());
							p.setFilm(film);
							p.setPrix(prices[new Random().nextInt(prices.length)]);
							p.setSalle(s);
							p.setSeance(seance);
							projectionRepo.save(p);
						
						
							
						});
					
				});
			});
				
		});
		
		
	}

	@Override
	public void initticket() {
		projectionRepo.findAll().forEach(pr->{
			pr.getSalle().getPlaces().forEach(p->{
				Ticket t=new Ticket();
				t.setPlace(p);
				t.setPrix(pr.getPrix());
				t.setProjection(pr);
				t.setReserver(false);
				ticketRepo.save(t);
				
			});
		});
		
	}

}

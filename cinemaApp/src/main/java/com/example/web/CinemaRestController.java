package com.example.web;

import java.io.File;
import org.springframework.ui.Model;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.FilmRepo;
import com.example.dao.TicketRepo;
import com.example.entities.Film;
import com.example.entities.Ticket;


import lombok.Data;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
	@Autowired
	private FilmRepo filmRepo;
	@Autowired
	private TicketRepo ticketRepo;
	
@GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] images(@PathVariable(name="id" )Long id)throws Exception
	{
		Film f=filmRepo.findById(id).get();
		String photoName=f.getPhoto();
		File file=new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
		Path path=Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}
@PostMapping("/payerTickets")
@Transactional
public List<Ticket> payerTicket(@RequestBody TicketForm ticketForm)
{
	List<Ticket>listTickets=new ArrayList<Ticket>();
	ticketForm.getTickets().forEach(t->{
		System.out.println(t);
		Ticket ticket=ticketRepo.findById(t).get();	
		ticket.setNomClient(ticketForm.getNomClient());
		ticket.setReserver(true);
		ticket.setCodePayement(ticketForm.getCodePayement());
		ticketRepo.save(ticket);
		listTickets.add(ticket);
	});
	return listTickets;
	
	
}






}
@Data
class TicketForm{
	private String nomClient;
	private int codePayement;
	private List<Long> tickets=new ArrayList<Long>();
}



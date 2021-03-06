package com.example.entities;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "p1",types =com.example.entities.Projection.class)
public interface ProjectionProj {

	public Long getId();
	public double getPrix();
	public Date getDateProjection();
	public Film getFilm();
	public Seance getSeance();
	public Salle getSalle();
	public Collection<Ticket> getTickets();
}

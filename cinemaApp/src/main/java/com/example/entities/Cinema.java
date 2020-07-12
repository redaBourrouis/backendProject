package com.example.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Cinema implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	 private Long id;
	@Column(length=75)
	 private String name;
	 private int nombreSalle;
	 private double longitude,latitude,altitude;
	 
	 @OneToMany(mappedBy ="cinema")
	 private Collection<Salle> salles;
	public Collection<Salle> getSalles() {
		return salles;
	}
	
	@ManyToOne
	 private Ville ville;
}
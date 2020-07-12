package com.example.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.example.entities.Projection;

@RepositoryRestResource
@CrossOrigin("*")
public interface ProjectionRepo extends JpaRepository<Projection, Long> {

	public Page<Projection> findByIdContains(String keyword,Pageable pageable);
}

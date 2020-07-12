package com.example.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.FilmRepo;
import com.example.dao.ProjectionRepo;
import com.example.dao.SalleRepo;
import com.example.dao.SeanceRepo;
import com.example.dao.VilleRepo;
import com.example.entities.Film;
import com.example.entities.Projection;
import com.example.entities.Salle;
import com.example.entities.Seance;

@Controller
public class FilmController {
	@Autowired
	private FilmRepo filmRepository;
	@Autowired
	private ProjectionRepo projectionRepository;
	@Autowired
	private SalleRepo salleRepo;
	@Autowired
	private SeanceRepo seanceRepo;

	
	@GetMapping(path = "/index")
	public String index() {
		return "index";
	}

	@GetMapping(path = "/layout")
	public String layout() {
		return "layout";
	}

	
	@GetMapping(path = "/filmsList")
	public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "20") int s,
			@RequestParam(name = "keyword", defaultValue = "") String kw) {
		Page<Film> pageFilms = filmRepository.findByTitreContains(kw, PageRequest.of(p, s));
		model.addAttribute("listefilms", pageFilms.getContent());
		model.addAttribute("pages", new int[pageFilms.getTotalPages()]);
		model.addAttribute("currentPage", p);
		model.addAttribute("keyword", kw);
		return "filmsView";
	}
	@GetMapping(path = "/proList")
	public String list2(Model model)
	{	
    List<Projection> projections=projectionRepository.findAll();
    model.addAttribute("projections", projections);
   
    return "projView";
	}
	

	@GetMapping(path = "/formFilmAdd")
	public String formFilmAdd(Model model) {
		model.addAttribute("film", new Film(null, null, null, null, null, 0, null, null, null));
		return "formFilmAdd";
	}
	@GetMapping(path = "/formProAdd")
	public String formProAdd(Model model) {
		model.addAttribute("projection", new Projection(null,null, 0, null, null, null, null));
		List<Film> films=filmRepository.findAll();
	    model.addAttribute("films",films);
	    List<Salle> salles=salleRepo.findAll();
	    model.addAttribute("salles",salles);
	    List<Seance> seances=seanceRepo.findAll();
	    model.addAttribute("seances",seances);
		return "formProAdd";
	}
	@GetMapping(path = "/formFilmEdit")
	public String formFilmEdit(Model model, Long id) {
		Film f = filmRepository.findById(id).get();
		model.addAttribute("film", f);
		return "formFilmEdit";
	}
	@GetMapping(path = "/formProEdit")
	public String formProEdit(Model model, Long id) {
		Projection f = projectionRepository.findById(id).get();
		model.addAttribute("projection", f);
		List<Film> films=filmRepository.findAll();
	    model.addAttribute("films",films);
	    List<Salle> salles=salleRepo.findAll();
	    model.addAttribute("salles",salles);
	    List<Seance> seances=seanceRepo.findAll();
	    model.addAttribute("seances",seances);
		return "formProEdit";
	}
	
	@GetMapping(path = "/deleteFilm")
	public String delete(Long id) {
		filmRepository.deleteById(id);
		return "redirect:/filmsList";
	}
	@GetMapping(path = "/deleteProjection")
	public String deletee(Long id) {
		projectionRepository.deleteById(id);
		return "redirect:/proList";
	}

	@PostMapping(path = "/saveFilm")
	public String saveFilm(Model model, @Valid Film film, BindingResult bindingresult) {
		if (bindingresult.hasErrors())
			return "formFilmAdd";
		filmRepository.save(film);
		model.addAttribute("film", film);
		return "confirm";
	}
	@PostMapping(path = "/saveProjection")
	public String saveFilm(Model model, @Valid Projection p, BindingResult bindingresult) {
		if (bindingresult.hasErrors())
			return "formProAdd";
		projectionRepository.save(p);
		model.addAttribute("Projection", p);
		return "confirmm";
	}
	@PostMapping(path = "/saveEditFilm")
	public String saveEditFilm(Model model, @Valid Film film, BindingResult bindingresult) {
		if (bindingresult.hasErrors())
			return "formFilmEdit";
		filmRepository.save(film);
		model.addAttribute("film", film);
		return "confirm";
	}
	@PostMapping(path = "/saveEditProjection")
	public String saveEditFilm(Model model, @Valid Projection p, BindingResult bindingresult) {
		if (bindingresult.hasErrors())
			return "formProEdit";
		projectionRepository.save(p);
		model.addAttribute("projection",p);
		return "confirmm";
	}
}

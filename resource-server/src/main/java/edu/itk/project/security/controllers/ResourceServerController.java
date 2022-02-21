package edu.itk.project.security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.itk.project.security.daos.MovieDAO;
import edu.itk.project.security.models.Movie;

@RestController
public class ResourceServerController {
	
	@Autowired
	MovieDAO movieDAO;
	
	@RequestMapping("/movies")
	public List<Movie> getAllMovies() {
		List<Movie> movies = movieDAO.getAllMovies();
		return movies;
	}
	
	@RequestMapping("/movies/{id}")
	public Movie getMovieById(@PathVariable("id") int id) {
		try {
			Movie movie = movieDAO.getMovieById(id);
			return movie;
		}
		catch(EmptyResultDataAccessException emptyResultDataAccessException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/movies/add")
	public Movie addMovie(@RequestBody Movie movie) {
		try {
			Movie result = movieDAO.addMovie(movie);
			return result;
		}
		catch(NullPointerException nullPointerException) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		catch(DataIntegrityViolationException dataIntegrityViolationException) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/movies/edit/{id}")
	public Movie editMovie(@RequestBody Movie movie, @PathVariable("id") int id)  {
		try {
			Movie result = movieDAO.editMovie(movie, id);
			return result;
		}
		catch(EmptyResultDataAccessException emptyResultDataAccessException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/movies/delete/{id}")
	public void deleteMovie(@PathVariable("id") int id) {
		try {
			Movie result = movieDAO.getMovieById(id);
			movieDAO.deleteMovie(id);
			throw new ResponseStatusException(HttpStatus.OK);
		}
		catch(EmptyResultDataAccessException emptyResultDataAccessException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
}

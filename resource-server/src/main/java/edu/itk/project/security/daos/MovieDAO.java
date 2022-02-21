package edu.itk.project.security.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import edu.itk.project.security.models.Movie;
import edu.itk.project.security.utils.MovieMapper;

@Service
public class MovieDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public MovieDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Movie> getAllMovies(){
		String query = "SELECT * FROM movies;";
		return jdbcTemplate.query(query, new MovieMapper());
	}
	
	@SuppressWarnings("deprecation")
	public Movie getMovieById(int id) throws EmptyResultDataAccessException {
		String query = "SELECT * FROM movies WHERE id = ?;";
		return jdbcTemplate.queryForObject(query, new Object[] { id }, new MovieMapper());
	}
	
	public Movie addMovie(Movie movie) throws NullPointerException, DataIntegrityViolationException {
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO movies (name, year, description) VALUES (?, ?, ?);", new String[] { "id" });
				preparedStatement.setString(1, movie.getName());
				preparedStatement.setInt(2, movie.getYear());
				preparedStatement.setString(3, movie.getDescription());
				return preparedStatement;
			}
		}, generatedKeyHolder);
		
		return getMovieById(generatedKeyHolder.getKey().intValue());
	}
	
	public Movie editMovie(Movie movie, int id) throws EmptyResultDataAccessException {
		Movie source = getMovieById(id);
		
		if(movie.getName() != null) {
			source.setName(movie.getName());
		}
		
		if(movie.getYear() != null) {
			source.setYear(movie.getYear());
		}
		
		if(movie.getDescription() != null) {
			source.setDescription(movie.getDescription());
		}
		
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement("UPDATE movies SET name = ?, year = ?, description = ? WHERE id = ?;", new String[] { "id" });
				preparedStatement.setString(1, source.getName());
				preparedStatement.setInt(2, source.getYear());
				preparedStatement.setString(3, source.getDescription());
				preparedStatement.setInt(4, source.getId());
				return preparedStatement;
			}
		}, generatedKeyHolder);
		
		return getMovieById(generatedKeyHolder.getKey().intValue());
	}
	
	public void deleteMovie(int id) {
		String query = "DELETE FROM movies WHERE id = ?;";
		jdbcTemplate.update(query, new Object[] { id });
	}
	
}

package edu.itk.project.security.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.itk.project.security.models.Movie;

public class MovieMapper implements RowMapper<Movie> {

	@Override
	public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
		Movie movie = new Movie();
		movie.setId(rs.getInt("id"));
		movie.setName(rs.getString("name"));
		movie.setYear(rs.getInt("year"));
		movie.setDescription(rs.getString("description"));
		return movie;
	}
	
}

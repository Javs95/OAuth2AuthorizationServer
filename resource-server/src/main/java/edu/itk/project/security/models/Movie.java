package edu.itk.project.security.models;

public class Movie {

	private Integer id;
	private String name;
	private Integer year;
	private String description;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Integer getYear() {
		return this.year;
	}
	
	public String getDescription() {
		return this.description;
	}
	
}

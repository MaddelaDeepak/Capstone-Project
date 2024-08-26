package com.dto;


public class DoctorDTO 
{
    private Integer id;

    private String name;

    private String specialization;

    private String expertise;
    
    private String availabile_time;
    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

	public String getAvailabile_time() {
		return availabile_time;
	}

	public void setAvailabile_time(String availabile_time) {
		this.availabile_time = availabile_time;
	}

  
}

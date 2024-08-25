package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="doctor")
public class Doctor 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Integer id;

    @Column(name="name")
    @NotEmpty(message="name missing")
    private String name;

    @Column(name="specialization")
    @NotEmpty(message="specialization missing")
    private String specialization;

    @Column(name="expertise")
    @NotEmpty(message="expertise missing")
    private String expertise;
    
    @Column(name="availabile_time")
    @NotEmpty(message="availabile_time missing")
    private String availabile_time;

	public Doctor(Integer id, String name, String specialization, String expertise, String availabile_time) 
	{
		super();
		this.id = id;
		this.name = name;
		this.specialization = specialization;
		this.expertise = expertise;
		this.availabile_time = availabile_time;
	}

	public Doctor() 
	{
		super();
	}

	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getSpecialization() 
	{
		return specialization;
	}

	public void setSpecialization(String specialization) 
	{
		this.specialization = specialization;
	}

	public String getExpertise() 
	{
		return expertise;
	}

	public void setExpertise(String expertise) 
	{
		this.expertise = expertise;
	}

	public String getAvailabile_time() 
	{
		return availabile_time;
	}

	public void setAvailabile_time(String availabile_time) 
	{
		this.availabile_time = availabile_time;
	}
    
    

}

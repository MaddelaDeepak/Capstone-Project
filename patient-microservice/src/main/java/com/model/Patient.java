package com.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="patient")
public class Patient 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Integer id;

    @Column(name="name")
    @NotEmpty(message="name missing")
    private String name;

    @Column(name="date_of_birth")
    @NotNull(message="date_of_birth missing")
    private LocalDate dateOfBirth;

    @Column(name="gender")
    @NotEmpty(message="gender missing")
    private String gender;
    
    @Column(name="contact")
    @NotEmpty(message="contact missing")
    private String contact;	
    
    @Column(name="email")
    @NotEmpty(message="email missing")
     private String email;	
    
    @Column(name="password")
    @NotEmpty(message="password missing")
    private String password;

	public Patient(Integer id, String name, LocalDate dateOfBirth, String gender, String contact, String email,
			String password) 
	{
		super();
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.contact = contact;
		this.email = email;
		this.password = password;
	}

	public Patient(String name, LocalDate dateOfBirth, String gender, String contact, String email, String password) 
	{
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.contact = contact;
		this.email = email;
		this.password = password;
	}

	public Patient() 
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

	public LocalDate getDateOfBirth() 
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) 
	{
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() 
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getContact() 
	{
		return contact;
	}

	public void setContact(String contact) 
	{
		this.contact = contact;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}
    
    
}

package com.repository;

import org.springframework.data.repository.CrudRepository;

import com.model.Patient;

public interface PatientRepository extends CrudRepository<Patient,Integer> 
{
	
}

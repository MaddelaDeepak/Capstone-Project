package com.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.model.Patient;

public interface PatientRepository extends CrudRepository<Patient,Integer> 
{
	Optional<Patient> findByEmailAndPassword(String email, String password);
	
	Optional<Patient> findNameById(Integer id);
}

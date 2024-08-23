package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.model.Doctor;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor,Integer>
{ 
	List<Doctor> findById(int id);
    List<Doctor> findBySpecialization(String specialization);
}

package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.CustomException;
import com.model.Doctor;
import com.repository.DoctorRepository;

@Service
public class DoctorService
{
	@Autowired
	DoctorRepository doctorRepos;
	public Optional<Doctor> findDoctorById(Integer id) throws CustomException 
	 {
		Optional<Doctor> doctor= doctorRepos.findById(id);
	    if(!doctor.isPresent())
	    {
	    	throw new CustomException("No doctor found for id:"+id);
	    }
	     return doctor;
	 }

	 public List<Doctor> findDoctorBySpecialization(String specialization) throws CustomException //throws CustomException 
	 {
	        List<Doctor> doctor = doctorRepos.findBySpecialization(specialization);
	        if (doctor.isEmpty()|| doctor==null) 
	        {
	            throw new CustomException("No Doctor found for specialization:"+specialization);
	        }
	        return doctor;
	    }
}

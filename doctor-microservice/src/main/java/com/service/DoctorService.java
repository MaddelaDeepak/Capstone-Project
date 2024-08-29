package com.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.CustomException;
import com.model.Doctor;
import com.repository.DoctorRepository;

@Service
public class DoctorService
{
	private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
	
	@Autowired
	DoctorRepository doctorRepos;
	public Iterable<Doctor> findAll()
	{
		return doctorRepos.findAll();
	}
	public Optional<Doctor> findDoctorById(Integer id) throws CustomException 
	 {
		logger.info("Attempting to find doctor by ID: {}", id);	
		Optional<Doctor> doctor= doctorRepos.findById(id);
	    if(!doctor.isPresent())
	    {
	    	logger.error("No doctor found for ID: {}", id);
	    	throw new CustomException("No doctor found for id:"+id);
	    }
	    logger.info("Doctor found for ID: {}", id);
	     return doctor;
	 }

	 public List<Doctor> findDoctorBySpecialization(String specialization) throws CustomException //throws CustomException 
	 {
		 logger.info("Attempting to find doctors by specialization: {}", specialization);
	     List<Doctor> doctor = doctorRepos.findBySpecialization(specialization);
	     if (doctor.isEmpty()|| doctor==null) 
	     {
	    	 logger.error("No doctors found for specialization: {}", specialization);
	    	 throw new CustomException("No Doctor found for specialization:"+specialization);
	     }
	     logger.info("Doctors found for specialization: {}", specialization);
	     return doctor;
	 }
}

package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Doctor;
import com.repository.DoctorRepository;

@Service
public class DoctorService
{
	@Autowired
	DoctorRepository doctorRepos;
	public List<Doctor> findDoctorById(int id)// throws CustomException 
	 {
	        List<Doctor> doctor = doctorRepos.findById(id);
	       /* if (doctor.isEmpty() || doctor==null) 
	        {
	            throw new CustomException("No song found");
	        }*/
	        return doctor;
	 }

	 public List<Doctor> findDoctorBySpecialization(String specialization) //throws CustomException 
	 {
	        List<Doctor> doctor = doctorRepos.findBySpecialization(specialization);
	       /* if (doctor.isEmpty()|| doctor==null) 
	        {
	            throw new CustomException("No song found");
	        }*/
	        return doctor;
	    }
}

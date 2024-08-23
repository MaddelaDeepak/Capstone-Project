package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Patient;
import com.repository.PatientRepository;

@Service
public class PatientService 
{
	@Autowired
	PatientRepository patientRepos;
	
	public Patient addNewPatient(Patient patient) 
	{
		Patient patient_=patientRepos.save(patient);
		return patientRepos.save(patient_);
	}
}

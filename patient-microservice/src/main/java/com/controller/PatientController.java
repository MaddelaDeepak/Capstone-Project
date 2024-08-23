package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Patient;
import com.repository.PatientFeignClient;
import com.service.PatientService;

@RestController
@Validated
@RequestMapping("/patients")
public class PatientController 
{
	@Autowired
	PatientService patientService;
	
	@Autowired
    private PatientFeignClient patientFeignClient; 
	
	@PostMapping("/register")
    public Patient addCustomer(@RequestBody Patient patient) 
    {
        return patientService.addNewPatient(patient);
    }
	
	@GetMapping("/doctors/{id}")
    public ResponseEntity<?> getDoctorDetailsById(@PathVariable Integer id) 
	{
    	return patientFeignClient.getDoctorById(id);
    }
	
	@GetMapping("/doctors")
    public ResponseEntity<?> getDoctorDetailsBySpecialization(@RequestParam String specialization) 
	{
    	return patientFeignClient.getDoctorBySpecialization(specialization);
    }
}

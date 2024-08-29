package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exception.CustomException;
import com.model.Doctor;
import com.service.DoctorService;

import jakarta.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Validated
@RequestMapping("/doctors")
public class DoctorController 
{
	private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
	
	@Autowired
	private DoctorService doctorService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable @Positive Integer id) throws CustomException 
	{
		logger.info("Received request to get doctor by ID: {}", id);
		Optional<Doctor> doctor = doctorService.findDoctorById(id);
		
		logger.info("Successfully retrieved doctor for ID: {}", id);
		return new ResponseEntity<>(doctor.get(), HttpStatus.OK);
	}
	@GetMapping("/")
    public ResponseEntity<String> handleMissingId() 
    {
		logger.warn("Doctor ID or Specialization is missing in the request");
        return new ResponseEntity<>("Doctor ID or Specialization is required", HttpStatus.BAD_REQUEST);
    }
	@GetMapping
	public ResponseEntity<?> getDoctorBySpecialization(@RequestParam(value="specialization",required=false) String specialization) throws CustomException 
	{
		logger.info("Received request to get doctors by specialization: {}", specialization);
		List<Doctor> doctor = doctorService.findDoctorBySpecialization(specialization);
		
		logger.info("Successfully retrieved doctors for specialization: {}", specialization);
		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}
}

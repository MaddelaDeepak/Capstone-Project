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

@RestController
@Validated
@RequestMapping("/doctors")
public class DoctorController 
{
	@Autowired
	private DoctorService doctorService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable Integer id) throws CustomException 
	{
		Optional<Doctor> doctor = doctorService.findDoctorById(id);
		return new ResponseEntity<>(doctor.get(), HttpStatus.OK);
	}
	@GetMapping
	public ResponseEntity<?> getDoctorBySpecialization(@RequestParam String specialization) throws CustomException 
	{
		List<Doctor> doctor = doctorService.findDoctorBySpecialization(specialization);
		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}
}

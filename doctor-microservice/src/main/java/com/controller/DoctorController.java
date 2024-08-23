package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> getDoctorById(@PathVariable Integer id) 
	{
		List<Doctor> doctor = doctorService.findDoctorById(id);
		/*if (doctor == null) {
			return new ResponseEntity<String>("No record found", HttpStatus.NO_CONTENT);
		}*/
		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}
	@GetMapping
	public ResponseEntity<?> getDoctorBySpecialization(@RequestParam String specialization) 
	{
		List<Doctor> doctor = doctorService.findDoctorBySpecialization(specialization);
		/*if (doctor == null) {
			return new ResponseEntity<String>("No record found", HttpStatus.NO_CONTENT);
		}*/
		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}
}

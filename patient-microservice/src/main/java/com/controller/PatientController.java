package com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AppointmentDTO;
import com.exception.CustomException;
import com.model.Appointment;
import com.model.Patient;
import com.repository.PatientFeignClient;
import com.service.PatientService;

import jakarta.validation.Valid;

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
    public Patient addCustomer(@Valid@RequestBody Patient patient) 
    {
        return patientService.addNewPatient(patient);
    }
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) 
	{
	    String result = patientService.login(email, password);
	    return ResponseEntity.ok(result);
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
	
	@PostMapping("/appointment")
	public Appointment bookAppointment(@Valid@RequestBody Appointment appointment) throws CustomException
    {
        return patientService.bookAppointment(appointment);
    }
	
	@GetMapping("/bookings/{id}")
	public ResponseEntity<?> getDoctorById(@PathVariable Integer id) throws CustomException 
	{	
		AppointmentDTO appointment = patientService.getAppointmentDetailsById(id);
		return new ResponseEntity<>(appointment, HttpStatus.OK);
	}

	/*@GetMapping("/bookings/{id}")
	public ResponseEntity<?> getDoctorById1(@PathVariable Integer id) throws CustomException 
	{
		Optional<Appointment> appointment = patientService.getAppointmentDetailsById1(id);
		return new ResponseEntity<>(appointment, HttpStatus.OK);
	}*/
}

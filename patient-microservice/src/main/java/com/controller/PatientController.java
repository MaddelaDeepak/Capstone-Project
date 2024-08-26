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
import com.dto.DoctorDTO;
import com.exception.CustomException;
import com.model.Appointment;
import com.model.Patient;
import com.repository.PatientFeignClient;
import com.service.PatientService;

import feign.FeignException;
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
    public ResponseEntity<?> addCustomer(@Valid@RequestBody Patient patient)
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
    public ResponseEntity<DoctorDTO> getDoctorDetailsById(@PathVariable Integer id) throws CustomException 
	{
		try 
		{
	        ResponseEntity<DoctorDTO> response = patientFeignClient.getDoctorById(id);
	        if (response.getStatusCode().is4xxClientError() || response.getBody() == null) 
	        {
	            throw new CustomException("No doctor found with ID: " + id);
	        }
	        return ResponseEntity.ok(response.getBody());
	    }
		catch (FeignException.NotFound e) 
		{
	        throw new CustomException("No doctor found with ID: " + id);
	    } 
    }
	
	@GetMapping("/doctors")
    public ResponseEntity<?> getDoctorDetailsBySpecialization(@RequestParam String specialization) throws CustomException 
	{
    	try 
		{
	        ResponseEntity<?> response = patientFeignClient.getDoctorBySpecialization(specialization);
	        if (response.getStatusCode().is4xxClientError() || response.getBody() == null) 
	        {
	            throw new CustomException("No doctor found with specialization: " + specialization);
	        }
	        return ResponseEntity.ok(response.getBody());
	    } 
    	catch (FeignException.NotFound e) 
		{
	        throw new CustomException("No doctor found with specialization: " + specialization);
	    }
    }
	
	@PostMapping("/appointment")
	public Appointment bookAppointment(@Valid@RequestBody Appointment appointment) throws CustomException
    {
        return patientService.bookAppointment(appointment);
    }
	
	@GetMapping("/bookings/{id}")
	public ResponseEntity<?> getAppointmentById(@PathVariable Integer id) throws CustomException 
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

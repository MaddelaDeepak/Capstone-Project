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
import com.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/patients")
public class PatientController 
{
	@Autowired
	PatientService patientService;
	
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
    public ResponseEntity<DoctorDTO> getDoctorDetailsById(@PathVariable @Positive @NotNull Integer id)
	{
		ResponseEntity<DoctorDTO> response = patientService.geDoctorById(id);
        return ResponseEntity.ok(response.getBody()); 
    }
	@GetMapping("/doctors/")
    public ResponseEntity<String> handleMissingId() 
    {
		return new ResponseEntity<>("Doctor ID is required", HttpStatus.BAD_REQUEST);
		
    }
	
	@GetMapping("/doctors")
    public ResponseEntity<?> getDoctorDetailsBySpecialization(@RequestParam(value="specialization",required=false) String specialization) 
	{
		ResponseEntity<?> response = patientService.getDoctorDetailsBySpecialization(specialization);
        return ResponseEntity.ok(response.getBody());
    }
	
	@PostMapping("/appointment")
	public Appointment bookAppointment(@Valid@RequestBody Appointment appointment) throws CustomException
    {
        return patientService.bookAppointment(appointment);
    }
	
	@GetMapping("/bookings/{id}")
	public ResponseEntity<?> getAppointmentById(@PathVariable @Positive Integer id) throws CustomException 
	{	
		AppointmentDTO appointment = patientService.getAppointmentDetailsById(id);
		return new ResponseEntity<>(appointment, HttpStatus.OK);
	}

}

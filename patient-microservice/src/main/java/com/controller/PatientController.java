package com.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/patients")
public class PatientController 
{
	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	PatientService patientService;
	
	@PostMapping("/register")
    public ResponseEntity<?> addCustomer(@Valid@RequestBody Patient patient)
    {
		logger.info("Received request to register a new patient with email: {}", patient.getEmail());
        ResponseEntity<?> response = patientService.addNewPatient(patient);
        
        logger.info("Patient registration completed for email: {}", patient.getEmail());
        return response;
    }
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam @Email String email, @RequestParam String password) 
	{
		logger.info("Received login request for email: {}", email);
	    String result = patientService.login(email, password);
	    
	    logger.info("Login process completed for email: {}", email);
	    return ResponseEntity.ok(result);
	}
	
	@GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorDTO> getDoctorDetailsById(@PathVariable @Positive @NotNull Integer id)
	{
		logger.info("Received request to get doctor details by ID: {}", id);
		ResponseEntity<DoctorDTO> response = patientService.geDoctorById(id);
		
		logger.info("Doctor details retrieval completed for ID: {}", id);
        return ResponseEntity.ok(response.getBody()); 
    }
	@GetMapping("/doctors/")
    public ResponseEntity<String> handleMissingId() 
    {
		logger.warn("Doctor ID is missing in the request");
		return new ResponseEntity<>("Doctor ID is required", HttpStatus.BAD_REQUEST);
    }
	
	@GetMapping("/doctors")
    public ResponseEntity<?> getDoctorDetailsBySpecialization(@RequestParam(value="specialization",required=false) String specialization) 
	{
		logger.info("Received request to get doctors by specialization: {}", specialization);
		ResponseEntity<?> response = patientService.getDoctorDetailsBySpecialization(specialization);
		
		logger.info("Doctor details retrieval by specialization completed: {}", specialization);
        return ResponseEntity.ok(response.getBody());
    }
	
	@PostMapping("/appointment")
	public Appointment bookAppointment(@Valid@RequestBody Appointment appointment) throws CustomException
    {
		logger.info("Received request to book appointment for patient ID: {}", appointment.getPatientId());
        Appointment bookedAppointment = patientService.bookAppointment(appointment);
        
        logger.info("Appointment booking completed for patient ID: {}", appointment.getPatientId());
        return bookedAppointment;
    }
	
	@GetMapping("{patientId}/bookings")
	public ResponseEntity<?> getAppointmentByPatientId(@PathVariable @Positive Integer patientId) 
	{	
		logger.info("Received request to get appointment details by Patient ID: {}", patientId);
		return patientService.getAppointmentDetailsPatientById(patientId);
	}

}

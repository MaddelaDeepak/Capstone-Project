package com.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dto.AppointmentDTO;
import com.dto.DoctorDTO;
import com.exception.CustomException;
import com.model.Appointment;
import com.model.Patient;
import com.repository.AppointmentRepository;
import com.repository.PatientFeignClient;
import com.repository.PatientRepository;

@Service
public class PatientService 
{
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

	@Autowired
	PatientRepository patientRepos;
	
	@Autowired
	AppointmentRepository appointmentRepos;
	
	@Autowired
    PatientFeignClient patientFeignClient;
	
	public ResponseEntity<DoctorDTO> geDoctorById(Integer id)
	{
		logger.info("Fetching doctor details by ID: {}", id);
		return patientFeignClient.getDoctorById(id);
	}
	
	 public ResponseEntity<?> getDoctorDetailsBySpecialization(String specialization)
	 {
		 logger.info("Fetching doctor details by specialization: {}", specialization);
		 return patientFeignClient.getDoctorBySpecialization(specialization);
	 }
	public ResponseEntity<?> addNewPatient(Patient patient)
	{
		logger.info("Attempting to register new patient with email: {}", patient.getEmail());

        Optional<Patient> existingPatient = patientRepos.findByEmail(patient.getEmail());
        if (existingPatient.isPresent()) 
        {
            String message = "Patient already registered with email: " + patient.getEmail();
            logger.warn(message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
		Patient patient_=patientRepos.save(patient);
		logger.info("Successfully registered new patient: {}", patient_);
		return new ResponseEntity<>(patient_, HttpStatus.OK);
	}
	public String login(String email, String password) 
	{
		logger.info("Attempting login for email: {}", email);
        Optional<Patient> patientOptional = patientRepos.findByEmailAndPassword(email, password);
        if (patientOptional.isPresent()) 
        {
        	logger.info("Login successful for email: {}", email);
            return "Valid PatientUser, successfully logged in.";
        } 
        else 
        {
        	logger.warn("Invalid login attempt for email: {}", email);
            return "Invalid PatientUser, email or password is incorrect.";
        }
    }
	
	public Appointment bookAppointment(Appointment appointment) throws CustomException
	{
		logger.info("Attempting to book appointment for patient ID: {}", appointment.getPatientId());
		Optional<Patient> patient =patientRepos.findNameById(appointment.getPatientId());
        if (!patient.isPresent()) 
        {
        	logger.error("No patient found with ID: {}", appointment.getPatientId());
            throw new CustomException("No patient found with ID: " + appointment.getPatientId());
        }
        ResponseEntity<DoctorDTO> response = patientFeignClient.getDoctorById(appointment.getDoctorId());
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT) || response.getBody() == null) 
        {
        	logger.error("No doctor found with ID: {}", appointment.getDoctorId());
            throw new CustomException("No doctor found with ID: " + appointment.getDoctorId());
        }
        DoctorDTO doctor =response.getBody();
        String availableTime=doctor.getAvailabile_time();
        String[] timeRange = availableTime.split("-");
        LocalTime startTime = LocalTime.parse(timeRange[0]);
        LocalTime endTime = LocalTime.parse(timeRange[1]);
       
        if (appointment.getAppointmentDate().isBefore(LocalDate.now())) 
        {
        	logger.error("Past date selected for appointment: {}", appointment.getAppointmentDate());
            throw new CustomException("Past dates can't be selected for the appointment.");
        }
        // Validate the appointment time slot
        if (appointment.getTimeSlot().isBefore(startTime) || appointment.getTimeSlot().isAfter(endTime)) 
        {
        	logger.error("Requested time slot {} is outside the doctor's available time: {}", appointment.getTimeSlot(), availableTime);
            throw new CustomException("Doctor is not available at the requested time slot: " + appointment.getTimeSlot());
        }
        Appointment appointment_=appointmentRepos.save(appointment);
        logger.info("Appointment successfully booked: {}", appointment_);
        return appointmentRepos.save(appointment_);    
        
    }
	
	public ResponseEntity<?> getAppointmentDetailsPatientById(int id) 
	{
	    logger.info("Fetching appointment details for Patient ID: {}", id);
	    
	    List<Appointment> appointments = appointmentRepos.findByPatientId(id);
	    
	    if (appointments.isEmpty()) 
	    {
	        logger.warn("No appointments found for Patient ID: {}", id);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("No appointments found for Patient ID: " + id);
	    }

	    List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
	    
	    for (Appointment appointment : appointments) 
	    {
	        ResponseEntity<DoctorDTO> response = patientFeignClient.getDoctorById(appointment.getDoctorId());
	        DoctorDTO doctor = response.getBody(); 
	        String doctorName = doctor.getName();
	        Optional<Patient> patientOptional = patientRepos.findNameById(appointment.getPatientId());	        
	        String patientName = patientOptional.get().getName();
	        
	        AppointmentDTO appointmentDTO = new AppointmentDTO();
	        appointmentDTO.setAppId(appointment.getAppId());
	        appointmentDTO.setDoctorName(doctorName);
	        appointmentDTO.setPatientName(patientName);
	        appointmentDTO.setAppointmentDate(appointment.getAppointmentDate());
	        appointmentDTO.setTimeSlot(appointment.getTimeSlot());
	        
	        appointmentDTOs.add(appointmentDTO);
	    }
	    
	    logger.info("Successfully retrieved appointment details for Doctor ID: {}", id);
	    return ResponseEntity.ok(appointmentDTOs);
	}
}

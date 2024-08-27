package com.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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

import feign.FeignException;

@Service
public class PatientService 
{
	@Autowired
	PatientRepository patientRepos;
	
	@Autowired
	AppointmentRepository appointmentRepos;
	
	@Autowired
    PatientFeignClient patientFeignClient;
	
	public ResponseEntity<DoctorDTO> geDoctorById(Integer id)
	{
		return patientFeignClient.getDoctorById(id);
	}
	
	 public ResponseEntity<?> getDoctorDetailsBySpecialization(String specialization)
	 {
		 return patientFeignClient.getDoctorBySpecialization(specialization);
	 }
	public ResponseEntity<?> addNewPatient(Patient patient)
	{
		// Check if a patient with the same email already exists
        Optional<Patient> existingPatient = patientRepos.findByEmail(patient.getEmail());
        
        if (existingPatient.isPresent()) 
        {
            // If patient already exists, return a custom message
            String message = "Patient already registered with email: " + patient.getEmail();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        // Validate that the date of birth is a past date
        if (patient.getDateOfBirth().isAfter(LocalDate.now())) 
        {
            // If the date of birth is not in the past, return a custom message
            String message = "Date of birth must be in the past.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
		Patient patient_=patientRepos.save(patient);
		return new ResponseEntity<>(patient_, HttpStatus.OK);
	}
	public String login(String email, String password) 
	{
        Optional<Patient> patientOptional = patientRepos.findByEmailAndPassword(email, password);
        if (patientOptional.isPresent()) 
        {
            return "Valid PatientUser, successfully logged in.";
        } 
        else 
        {
            return "Invalid PatientUser, email or password is incorrect.";
        }
    }
	
	public Appointment bookAppointment(Appointment appointment) throws CustomException
	{
		
		Optional<Patient> patient =patientRepos.findNameById(appointment.getPatientId());
        if (!patient.isPresent()) 
        {
            throw new CustomException("No patient found with ID: " + appointment.getPatientId());
        }
        try 
        {
        	ResponseEntity<DoctorDTO> response = patientFeignClient.getDoctorById(appointment.getDoctorId());
            if (response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT) || response.getBody() == null) 
            {
                throw new CustomException("No doctor found with ID: " + appointment.getDoctorId());
            }
            DoctorDTO doctor =response.getBody();
            String availableTime=doctor.getAvailabile_time();
            String[] timeRange = availableTime.split("-");
            LocalTime startTime = LocalTime.parse(timeRange[0]);
            LocalTime endTime = LocalTime.parse(timeRange[1]);
         // Check if the appointment date is in the past
            if (appointment.getAppointmentDate().isBefore(LocalDate.now())) 
            {
            	System.out.println(LocalDate.now());
                throw new CustomException("Past dates can't be selected for the appointment.");
            }
            // Validate the appointment time slot
            if (appointment.getTimeSlot().isBefore(startTime) || appointment.getTimeSlot().isAfter(endTime)) 
            {
                throw new CustomException("Doctor is not available at the requested time slot: " + appointment.getTimeSlot());
            }
        }
        catch (FeignException e) 
        {
        	throw new CustomException("No doctor found with ID: " + appointment.getDoctorId());
        }
        Appointment appointment_=appointmentRepos.save(appointment);
        return appointmentRepos.save(appointment_);
    }
	
	public AppointmentDTO getAppointmentDetailsById(int id) throws CustomException 
	{
	    Optional<Appointment> appointmentOptional = appointmentRepos.findById(id);
	    
	    if (!appointmentOptional.isPresent()) 
	    {
	        throw new CustomException("No appointment found for application id: " + id);
	    }

	    Appointment appointment = appointmentOptional.get();
	    
	    ResponseEntity<DoctorDTO> response =patientFeignClient.getDoctorById(appointment.getDoctorId());
	    
		DoctorDTO doctor = response.getBody();
	    String doctorName = doctor.getName();
	    Optional<Patient> patient = patientRepos.findNameById(appointment.getPatientId());
	    String patientName = patient.get().getName();
	    
	    AppointmentDTO appointmentDTO = new AppointmentDTO();
	    appointmentDTO.setAppId(appointment.getAppId());
	    appointmentDTO.setDoctorName(doctorName);
	    appointmentDTO.setPatientName(patientName);
	    appointmentDTO.setAppointmentDate(appointment.getAppointmentDate());
	    appointmentDTO.setTimeSlot(appointment.getTimeSlot());
	    
	    return appointmentDTO;
	}
}

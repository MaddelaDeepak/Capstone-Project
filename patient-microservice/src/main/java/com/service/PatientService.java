package com.service;

import java.time.LocalTime;
import java.util.Map;
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
	
	public Patient addNewPatient(Patient patient) 
	{
		Patient patient_=patientRepos.save(patient);
		return patientRepos.save(patient_);
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
	    
	    ResponseEntity<?> response =patientFeignClient.getDoctorById(appointment.getDoctorId());
	    
	    @SuppressWarnings("unchecked")
		Map<String, Object> doctorMap = (Map<String, Object>) response.getBody();
	    String doctorName = (String) doctorMap.get("name");
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

	/*public Optional<Appointment> getAppointmentDetailsById(int id) throws CustomException 
	{
		Optional<Appointment> appointment =  appointmentRepos.findById(id);
		if (!appointment.isPresent()) 
        {
            throw new CustomException("No appointment found for application id : "+id);
        }
		
		return appointment;
	}*/
}

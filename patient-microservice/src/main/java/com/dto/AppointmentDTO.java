package com.dto;

import java.time.LocalDate;
import java.time.LocalTime;


public class AppointmentDTO 
{
    private Integer appId;

    private String patientName;
    
    private String doctorName;
    
    private LocalDate appointmentDate;

    private LocalTime timeSlot;

	public AppointmentDTO(Integer appId, String patientName, String doctorName, LocalDate appointmentDate,
			LocalTime timeSlot) 
	{
		super();
		this.appId = appId;
		this.patientName = patientName;
		this.doctorName = doctorName;
		this.appointmentDate = appointmentDate;
		this.timeSlot = timeSlot;
	}

	public AppointmentDTO() 
	{
		super();
	}

	public Integer getAppId() 
	{
		return appId;
	}

	public void setAppId(Integer appId) 
	{
		this.appId = appId;
	}

	public String getPatientName() 
	{
		return patientName;
	}

	public void setPatientName(String patientName) 
	{
		this.patientName = patientName;
	}

	public String getDoctorName() 
	{
		return doctorName;
	}

	public void setDoctorName(String doctorName) 
	{
		this.doctorName = doctorName;
	}

	public LocalDate getAppointmentDate() 
	{
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) 
	{
		this.appointmentDate = appointmentDate;
	}

	public LocalTime getTimeSlot() 
	{
		return timeSlot;
	}

	public void setTimeSlot(LocalTime timeSlot) 
	{
		this.timeSlot = timeSlot;
	}
    
    
}

package com.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="appointment")
public class Appointment 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="app_id")
    private Integer appId;

    @Column(name="patient_id")
    @NotNull(message="patientId missing")
    private Integer patientId;
    
    @Column(name="doctor_id")
    @NotNull(message="doctorId missing")
    private Integer doctorId;
    
    @Column(name="appointment_date")
    @NotNull(message="appointmentDate missing")
    private LocalDate appointmentDate;

    @Column(name="time_slot")
    @NotNull(message="timeSlot missing")
    private LocalTime timeSlot;
    

	public Appointment(Integer appId, Integer patientId, Integer doctorId, LocalDate appointmentDate,
			LocalTime timeSlot) 
	{
		super();
		this.appId = appId;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.appointmentDate = appointmentDate;
		this.timeSlot = timeSlot;
	}

	public Appointment(Integer patientId, Integer doctorId, LocalDate appointmentDate, LocalTime timeSlot) {
		super();
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.appointmentDate = appointmentDate;
		this.timeSlot = timeSlot;
	}

	public Appointment() 
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

	public Integer getPatientId()
	{
		return patientId;
	}

	public void setPatientId(Integer patientId) 
	{
		this.patientId = patientId;
	}

	public Integer getDoctorId() 
	{
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) 
	{
		this.doctorId = doctorId;
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

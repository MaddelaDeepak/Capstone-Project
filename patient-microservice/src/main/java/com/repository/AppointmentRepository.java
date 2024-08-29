package com.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.model.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment,Integer>
{
	List<Appointment> findByPatientId(int id);
}

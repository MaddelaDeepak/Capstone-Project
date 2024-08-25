package com.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.model.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment,Integer>
{
	Optional<Appointment> findById(int id);
}

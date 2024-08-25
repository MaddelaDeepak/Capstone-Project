package com.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.DoctorDTO;

@FeignClient(name="DOCTORMICROSERVICE")
public interface PatientFeignClient 
{
	@GetMapping("/doctors/{id}")
	public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Integer id);
	
	@GetMapping("/doctors")
	public ResponseEntity<?> getDoctorBySpecialization(@RequestParam String specialization);

}

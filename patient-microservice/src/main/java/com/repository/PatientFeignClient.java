package com.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.config.FeignConfig;
import com.dto.DoctorDTO;

@FeignClient(name="DOCTORMICROSERVICE",configuration = FeignConfig.class)
public interface PatientFeignClient 
{
	@GetMapping("/doctors/{id}")
	public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Integer id);
	
	@GetMapping("/doctors/")
    public ResponseEntity<String> handleMissingId();
	
	@GetMapping("/doctors")
	public ResponseEntity<?> getDoctorBySpecialization(@RequestParam String specialization);

}

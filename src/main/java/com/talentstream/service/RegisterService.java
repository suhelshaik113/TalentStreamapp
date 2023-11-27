package com.talentstream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talentstream.entity.Applicant;
import com.talentstream.repository.JobRecruiterRepository;
import com.talentstream.repository.RegisterRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RegisterService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JobRecruiterRepository recruiterRepository;

	 @Autowired
	RegisterRepository applicantRepository;
    public RegisterService( RegisterRepository applicantRepository) {
	        this.applicantRepository = applicantRepository;
	    }

 

public Applicant login(String email, String password) {
	Applicant applicant = applicantRepository.findByEmail(email);
	 if (applicant!= null && passwordEncoder.matches(password, applicant.getPassword())) {
         return applicant ; 
     }  else {
         return null; 
     }

}

public Applicant findById(Long id) {
    return applicantRepository.findById(id);
   }

public List<Applicant> getAllApplicants() {
     return applicantRepository.findAll();

}

public void updatePassword(String userEmail, String newPassword) {
	 Applicant jobRecruiter = applicantRepository.findByEmail(userEmail);
    if (jobRecruiter != null) {
         jobRecruiter.setPassword(newPassword);
         applicantRepository.save(jobRecruiter);

     } else {
         throw new EntityNotFoundException("JobRecruiter not found for email: " + userEmail);

 }

}

 

	public Applicant findByEmail(String userEmail) {

		return applicantRepository.findByEmail(userEmail);

	}

 

	public Applicant findByEmailAddress(String userEmail) {

		return applicantRepository.findByEmail(userEmail);

	}

 

	public ResponseEntity<String> saveapplicant(Applicant applicant) {

		if (applicantRepository.existsByEmail(applicant.getEmail())||recruiterRepository.existsByEmail(applicant.getEmail())) {
	         return ResponseEntity.badRequest().body("Email already registered");

	     }

	   	     applicant.setPassword(passwordEncoder.encode(applicant.getPassword()));
	        	     applicantRepository.save(applicant);
	     return ResponseEntity.ok("Applicant registered successfully");

	}



	

}
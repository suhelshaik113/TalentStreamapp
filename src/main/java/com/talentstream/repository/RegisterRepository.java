package com.talentstream.repository;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

 

 

import com.talentstream.entity.Applicant;

 

@Repository

public interface RegisterRepository extends JpaRepository<Applicant, Integer> {
	Applicant  findByEmail(String email);
	boolean existsByEmail(String email);
	Applicant findById(Long id);

	Applicant getApplicantById(long applicantid);

 

	

}
package com.talentstream.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentstream.entity.ApplicantProfile;

@Repository
public interface ApplicantProfileRepository extends JpaRepository<ApplicantProfile, Integer> {

ApplicantProfile findByApplicantId(long applicantid);

	


}
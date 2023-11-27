package com.talentstream.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentstream.entity.Applicant;
import com.talentstream.entity.ApplicantProfile;
import com.talentstream.repository.ApplicantProfileRepository;
import com.talentstream.repository.RegisterRepository;

@Service
public class ApplicantProfileService {
	 private final ApplicantProfileRepository applicantProfileRepository;
	 
	 
	 private final RegisterRepository applicantService;

	    @Autowired
	    public ApplicantProfileService(ApplicantProfileRepository applicantProfileRepository,RegisterRepository applicantService) {
	        this.applicantProfileRepository = applicantProfileRepository;
	        this.applicantService=applicantService;
	    }

	    public String createOrUpdateApplicantProfile(long applicantId, ApplicantProfile applicantProfile) {
	    	Applicant applicant = applicantService.getApplicantById(applicantId);
	    	
	    	ApplicantProfile existingProfile = applicantProfileRepository.findByApplicantId(applicantId);
 
	        if (existingProfile == null) {
	        	applicantProfile.setApplicant(applicant);
	        	applicantProfileRepository.save(applicantProfile);
	        	System.out.println("Profile saved successfully");
	        	return "Profile saved successfully";
	              
	              
	        } else {
	        	System.out.println("your Profile was updated already");
	            return "your Profile was updated already"; // Profile for this applicant already exists
	        }
	    }


	    public ApplicantProfile getApplicantProfileById(long applicantId) {
	        return applicantProfileRepository.findById((int) applicantId).orElse(null);
	    }

	    public void deleteApplicantProfile(long applicantId) {
	        applicantProfileRepository.deleteById((int) applicantId);
	    }

}

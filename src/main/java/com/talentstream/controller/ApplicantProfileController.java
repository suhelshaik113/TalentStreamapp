package com.talentstream.controller;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentstream.entity.Applicant;
import com.talentstream.entity.ApplicantProfile;
import com.talentstream.repository.RegisterRepository;
import com.talentstream.service.ApplicantProfileService;
import com.talentstream.service.RegisterService;

@CrossOrigin("*")
@RestController
@RequestMapping("/applicantprofile")
public class ApplicantProfileController {
	private final ApplicantProfileService applicantProfileService;
	private final RegisterRepository applicantService;
	
    @Autowired
    public ApplicantProfileController(ApplicantProfileService applicantProfileService,RegisterRepository applicantService) {
        this.applicantProfileService = applicantProfileService;
		this.applicantService = applicantService;
    }

    @PostMapping("/createprofile/{applicantid}")
    public String createOrUpdateApplicantProfile(@PathVariable long applicantid,@RequestBody ApplicantProfile applicantProfile) {
    	 Applicant applicant = applicantService.getApplicantById(applicantid);
         if (applicant == null) {
             return "Applicant not found";
         }
         else
         {
        	 //applicantProfile.setApplicant(applicant);
	   return  applicantProfileService.createOrUpdateApplicantProfile(applicantid,applicantProfile);
       // return ResponseEntity.ok(savedProfile);
         }
    }
   @GetMapping("/getdetails/{applicantid}")
   public ResponseEntity<ApplicantProfile> getApplicantProfileById(@PathVariable int applicantid) {
       ApplicantProfile applicantProfile = applicantProfileService.getApplicantProfileById(applicantid);
       if (applicantProfile != null) {
           return ResponseEntity.ok(applicantProfile);
       } else {
           return ResponseEntity.notFound().build();
       }
   }
       @DeleteMapping("/deletedetails/{applicantId}")
       public ResponseEntity<Void> deleteApplicantProfile(@PathVariable int applicantId) {
           applicantProfileService.deleteApplicantProfile(applicantId);
           return ResponseEntity.noContent().build();
       }
}

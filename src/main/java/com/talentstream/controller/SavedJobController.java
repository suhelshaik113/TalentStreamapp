package com.talentstream.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentstream.entity.Job;
import com.talentstream.service.SavedJobService;

@RestController
@RequestMapping("/savedjob")
public class SavedJobController {
	 @Autowired
	    private SavedJobService savedJobService;

	    @PostMapping("/applicants/savejob/{applicantId}/{jobId}")
	    public ResponseEntity<String> saveJobForApplicant(
	            @PathVariable long applicantId,
	            @PathVariable long jobId
	    ) {
	    	 try {
	             savedJobService.saveJobForApplicant(applicantId, jobId);
	             return ResponseEntity.ok("Job saved successfully for the applicant.");
	         } catch (Exception e) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	         }
	     }

	    
    @GetMapping("/getSavedJobs/{applicantId}")
	    public ResponseEntity<List<Job>> getSavedJobsForApplicantAndJob(
	            @PathVariable long applicantId
	            
	    ) {
	        try {
            List<Job> savedJobs = savedJobService.getSavedJobsForApplicant(applicantId);
	            return ResponseEntity.ok(savedJobs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
	        }
	    }
}

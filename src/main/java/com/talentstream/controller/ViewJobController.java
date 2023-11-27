package com.talentstream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentstream.entity.Job;
import com.talentstream.repository.JobRepository;

@RestController
@RequestMapping("/viewjob")
public class ViewJobController {
	   @Autowired
	    private JobRepository jobRepository;
	

    //@GetMapping("/applicant/viewjob/{jobId}/{applicantId}")
	   @GetMapping("/applicant/viewjob/{jobId}")
    public ResponseEntity<?> getJobDetailsForApplicant(
                      @PathVariable Long jobId) {
    	
    	Job job = jobRepository.findById(jobId).orElse(null);

    	if (job != null) {
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

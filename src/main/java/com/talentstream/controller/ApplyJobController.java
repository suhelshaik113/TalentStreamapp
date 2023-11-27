package com.talentstream.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentstream.entity.ApplicantJobInterviewDTO;
import com.talentstream.entity.AppliedApplicantInfoDTO;
import com.talentstream.entity.Job;
import com.talentstream.entity.ScheduleInterview;
import com.talentstream.service.ApplyJobService;
import com.talentstream.service.ScheduleInterviewService;
@RestController
@RequestMapping("/applyjob")
public class ApplyJobController {
	 @Autowired
	    private ApplyJobService applyJobService;
	 @Autowired
	    private ScheduleInterviewService scheduleInterviewService;
	 
	 @PostMapping("/applicants/applyjob/{applicantId}/{jobId}")
	    public String saveJobForApplicant(
	            @PathVariable long applicantId,
	            @PathVariable long jobId
	    ) {
	       
		 return applyJobService.ApplicantApplyJob(applicantId, jobId);
	          
	        
      }
	    
 @GetMapping("/getAppliedJobs/{applicantId}")
	    public ResponseEntity<List<Job>> getAppliedJobsForApplicant(
	            @PathVariable long applicantId
	            
	    ) {
	        try {
         List<Job> AppliedJobs = applyJobService.getAppliedJobsForApplicant(applicantId);
	            return ResponseEntity.ok(AppliedJobs);
     } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
	        }
	    }
 @GetMapping("/recruiter/{jobRecruiterId}/appliedapplicants")
 public ResponseEntity<List<AppliedApplicantInfoDTO>> getAppliedApplicantsForRecruiter(@PathVariable long jobRecruiterId) {
     List<AppliedApplicantInfoDTO> appliedApplicants = applyJobService. getAppliedApplicants(jobRecruiterId);
     return ResponseEntity.ok(appliedApplicants);
  
 }
   @PostMapping("/recruiters/scheduleInterview/{applyJobId}")
   public ResponseEntity<Void> createScheduleInterview(
           @PathVariable Long applyJobId,
           @RequestBody ScheduleInterview scheduleInterview) {
  	 ScheduleInterview isCreated = scheduleInterviewService.createScheduleInterview(applyJobId,scheduleInterview);
       if (isCreated!=null) {
           return new ResponseEntity<>(HttpStatus.CREATED);
       } else {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
   
   @PostMapping("/recruiters/applyjob-update-status/{applyJobId}/{newStatus}")
   public ResponseEntity<String> updateApplicantStatus(
           @PathVariable Long applyJobId,
           @PathVariable String newStatus) {
       String updateMessage = applyJobService.updateApplicantStatus(applyJobId, newStatus);
       return ResponseEntity.ok(updateMessage);
   }
   @GetMapping("/recruiter/{recruiterId}/interviews/{status}")
   public List<ApplicantJobInterviewDTO> getApplicantJobInterviewInfo(
           @PathVariable("recruiterId") long recruiterId,
           @PathVariable("status") String status) {
       return applyJobService.getApplicantJobInterviewInfoForRecruiterAndStatus(recruiterId, status);
   }
   
   @GetMapping("/recruiters/applyjobapplicantscount/{recruiterId}")
   public ResponseEntity<Long> countJobApplicantsByRecruiterId( @PathVariable Long recruiterId) {
       long count = applyJobService.countJobApplicantsByRecruiterId(recruiterId);
       return ResponseEntity.ok(count);
   }
   
   @GetMapping("/recruiters/selected/count")
   public long countSelectedApplicants() {
       // Call the service method to get the count of selected applicants
       return applyJobService.countSelectedApplicants();
   }
   
   @GetMapping("/recruiters/countShortlistedAndInterviewed")
   public ResponseEntity<Long> countShortlistedAndInterviewedApplicants() {
       long count = applyJobService.countShortlistedAndInterviewedApplicants();
       return ResponseEntity.ok(count);
   }
   
   @GetMapping("/current-date")
   public ResponseEntity<List<ScheduleInterview>> getScheduleInterviewsForCurrentDate() {
       List<ScheduleInterview> scheduleInterviews = scheduleInterviewService.getScheduleInterviewsForCurrentDate();
       return ResponseEntity.ok(scheduleInterviews);
   }
   @GetMapping("/getScheduleInterviews/applicant/{applyJobId}/{applicantId}")
   public List<ScheduleInterview> getScheduleInterviews(
           @PathVariable Long applicantId, @PathVariable Long applyJobId) {
       return scheduleInterviewService.getScheduleInterviewsByApplicantAndApplyJob(applicantId, applyJobId);
   }
 }



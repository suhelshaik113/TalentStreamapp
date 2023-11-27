package com.talentstream.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talentstream.entity.Job;
import com.talentstream.entity.JobRecruiter;
import com.talentstream.repository.JobRecruiterRepository;
import com.talentstream.service.JobService;

@RestController
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;
    @Autowired
     JobRecruiterRepository jobRecruiterRepository;
    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    
    @PostMapping("/recruiters/saveJob/{jobRecruiterId}")
    public ResponseEntity<String> saveJob(@RequestBody @Valid Job job, @PathVariable Long jobRecruiterId) {
        // Associate eligibility with the job
        JobRecruiter jobRecruiter = jobRecruiterRepository.findByRecruiterId(jobRecruiterId);

        if (jobRecruiter != null) {
            // Associate the JobRecruiter with the CompanyProfile
            job.setJobRecruiter(jobRecruiter);
            // Save the CompanyProfile with the associated JobRecruiter
            jobService.saveJob(job);
            return ResponseEntity.status(HttpStatus.OK).body("Job saved successfully.");
        } else {
            // Handle the case where the JobRecruiter with the provided ID does not exist
            // You can return an error response with a 404 Not Found status code
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JobRecruiter with ID " + jobRecruiterId + " not found.");
        }
    }
    @GetMapping("/recruiters/viewJobs/{jobRecruiterId}")
    public ResponseEntity<List<Job>> getJobsByRecruiter(@PathVariable Long jobRecruiterId) {
        // You can add validation here to ensure the jobRecruiterId is valid.

        List<Job> jobs = jobService.getJobsByRecruiter(jobRecruiterId);
        if (jobs.isEmpty()) {
            // If no jobs are found for the specified recruiter, you can return a not found response.
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(jobs);
        }
    }
    
    @GetMapping("/recruiterssearchBySkillName")
    public List<Job> searchBySkillName(
        @RequestParam("skillName") String skillName
        ) {
    	System.out.println(skillName);
        return  jobService.searchBySkillName(skillName);
      
    }
    
    @GetMapping("/recruiters/viewJobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/recruiters/{jobId}")
    public ResponseEntity<Job> getJobById(@PathVariable Long jobId) {
        Job job = jobService.getJobById(jobId);
        if (job != null) {
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/recruiterssearchByJobTitle")
    public ResponseEntity<List<Job>> searchByJobTitle(@RequestParam String jobTitle) {
        List<Job> jobs = jobService.searchByJobTitle(jobTitle);
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/recruiterssearchByLocation")
    public ResponseEntity<List<Job>> searchByLocation(@RequestParam String location) {
        List<Job> jobs = jobService.searchByLocation(location);
        System.out.println(jobs);
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/recruiterssearchByIndustryType")
    public ResponseEntity<List<Job>> searchByIndustryType(@RequestParam String industryType) {
        List<Job> jobs = jobService.searchByIndustryType(industryType);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/recruiterssearchByEmployeeType")
    public ResponseEntity<List<Job>> searchByEmployeeType(@RequestParam String employeeType) {
        List<Job> jobs = jobService.searchByEmployeeType(employeeType);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/searchByMinimumQualification")
    public ResponseEntity<List<Job>> searchByMinimumQualification(@RequestParam String minimumQualification) {
        List<Job> jobs = jobService.searchByMinimumQualification(minimumQualification);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/recruiterssearchBySpecialization")
    public ResponseEntity<List<Job>> searchBySpecialization(@RequestParam String specialization) {
        List<Job> jobs = jobService.searchBySpecialization(specialization);
        return ResponseEntity.ok(jobs);
    }
    @GetMapping("/recruiterssearchBySkillNameAndExperience")
    public ResponseEntity<List<Job>> searchBySkillNameAndExperience(
        @RequestParam String skillName,
        @RequestParam int minimumExperience) {
        List<Job> jobs = jobService.searchBySkillNameAndExperience(skillName, minimumExperience);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/recruiterscountjobs/{recruiterId}")
    public ResponseEntity<Long> countJobsByRecruiter(@PathVariable Long recruiterId) {
        long jobCount = jobService.countJobsByRecruiterId(recruiterId);
        return ResponseEntity.ok(jobCount);
    }
  
}

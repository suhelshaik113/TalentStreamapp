package com.talentstream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentstream.entity.Job;

import com.talentstream.repository.JobRepository;
import com.talentstream.repository.RecuriterSkillsRepository;

@Service
public class JobService {
	JobRepository jobRepository;	   
	    private RecuriterSkillsRepository skillsRepository;
	    
    @Autowired
    public JobService(JobRepository jobRepository, RecuriterSkillsRepository skillsRepository) {
        this.jobRepository = jobRepository;
        this.skillsRepository = skillsRepository;
    }

    public List<Job> searchBySkillName(String skillName) {
		return jobRepository.findJobsBySkillNameIgnoreCase(skillName);
	}

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }
    public List<Job> searchByJobTitle(String jobTitle) {
        return jobRepository.findByJobTitleContainingIgnoreCase(jobTitle);
    }

    public List<Job> searchByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Job> searchByIndustryType(String industryType) {
        return jobRepository.findByIndustryTypeContainingIgnoreCase(industryType);
    }
    public List<Job> searchByEmployeeType(String employeeType) {
        return jobRepository.findByEmployeeTypeContainingIgnoreCase(employeeType);
    }
    public List<Job> searchByMinimumQualification(String minimumQualification) {
        return jobRepository.findByMinimumQualificationContainingIgnoreCase(minimumQualification);
    }
    public List<Job> searchBySpecialization(String specialization) {
        return jobRepository.findBySpecializationContainingIgnoreCase(specialization);
    }
    public List<Job> searchBySkillNameAndExperience(String skillName, int minimumExperience) {
        return jobRepository.findBySkillsRequiredSkillNameAndSkillsRequiredMinimumExperience(skillName, minimumExperience);
    }
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    public List<Job> getJobsByRecruiter(Long jobRecruiterId) {
        // You need to implement the logic to retrieve jobs by recruiter ID.
        // This depends on how your data is structured and your data access layer.
        // Here's a simplified example assuming you have a method in the repository.

 

        // You should implement this method in your repository.
        // Replace "findByJobRecruiterId" with the actual method you use to retrieve jobs by recruiter ID.
        List<Job> jobs = jobRepository.findByJobRecruiterId(jobRecruiterId);

 

        return jobs;
    }
    



	public long countJobsByRecruiterId(Long recruiterId) {
		return jobRepository.countJobsByRecruiterId(recruiterId);
	}
    
}
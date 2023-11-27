package com.talentstream.service;


import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.talentstream.entity.ApplicantProfile;
import com.talentstream.entity.ApplicantSkills;
import com.talentstream.entity.Job;
import com.talentstream.repository.ApplicantProfileRepository;
import com.talentstream.repository.JobRepository;


 

@Service
public class FinRecommendedJobService {

	@Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicantProfileRepository applicantRepository;
    
    @Autowired
    public FinRecommendedJobService(JobRepository jobRepository, ApplicantProfileRepository applicantRepository) {
        this.jobRepository = jobRepository;
        this.applicantRepository = applicantRepository;
    }
    public List<Job> findJobsMatchingApplicantSkills(long applicantId) {
        ApplicantProfile applicantProfile = applicantRepository.findByApplicantId(applicantId);

        if (applicantProfile == null) {
           
            return Collections.emptyList();
        }

        Set<ApplicantSkills> applicantSkills = applicantProfile.getSkillsRequired();
        Set<String> lowercaseApplicantSkillNames = applicantSkills.stream()
                .map(skill -> skill.getSkillName().toLowerCase())
                .collect(Collectors.toSet());

        // Query the database using a custom method for case-insensitive and partial matching
        List<Job> matchingJobs = jobRepository.findBySkillsRequiredIgnoreCaseAndSkillNameIn(lowercaseApplicantSkillNames);

        return matchingJobs;
    }
}









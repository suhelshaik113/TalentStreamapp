package com.talentstream.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentstream.entity.ApplyJob;
import com.talentstream.entity.ScheduleInterview;
import com.talentstream.repository.ApplyJobRepository;
import com.talentstream.repository.ScheduleInterviewRepository;

@Service
public class ScheduleInterviewService {
	  @Autowired
	ApplyJobRepository applyJobRepository;

    @Autowired
    private ScheduleInterviewRepository scheduleInterviewRepository;

    public ScheduleInterview createScheduleInterview(Long applyJobId, ScheduleInterview scheduleInterview) {
        // Find the ApplyJob based on the given applyJobId
        ApplyJob applyJob = applyJobRepository.findById(applyJobId)
                .orElseThrow(() -> new EntityNotFoundException("ApplyJob not found"));

        // Set the ApplyJob for the ScheduleInterview
        scheduleInterview.setApplyJob(applyJob);

        // Save the ScheduleInterview with the ApplyJob association
        return scheduleInterviewRepository.save(scheduleInterview);
    }
    // Add more service methods as needed
    
    public List<ScheduleInterview> getScheduleInterviewsForCurrentDate() {
        return scheduleInterviewRepository.findScheduleInterviewsForCurrentDate();
    }

    public List<ScheduleInterview> getScheduleInterviewsByApplicantAndApplyJob(Long applicantId, Long applyJobId) {
        return scheduleInterviewRepository.findByApplicantIdAndApplyJobId(applicantId, applyJobId);
    }
}
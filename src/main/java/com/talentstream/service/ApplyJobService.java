package com.talentstream.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.talentstream.entity.Applicant;
import com.talentstream.entity.ApplicantJobInterviewDTO;
import com.talentstream.entity.AppliedApplicantInfo;
import com.talentstream.entity.AppliedApplicantInfoDTO;
import com.talentstream.entity.ApplyJob;
import com.talentstream.entity.Job;
import com.talentstream.entity.SavedJob;
import com.talentstream.repository.ApplyJobRepository;
import com.talentstream.repository.JobRepository;
import com.talentstream.repository.RegisterRepository;
import com.talentstream.repository.ScheduleInterviewRepository;
 
 
import jakarta.persistence.EntityNotFoundException;
 
 
@Service
public class ApplyJobService {
	 @Autowired
	   private ApplyJobRepository applyJobRepository;	
	  
	 @Autowired
	   private ScheduleInterviewRepository scheduleInterviewRepository;	
	 	    
	    @Autowired
	    private JobRepository jobRepository;
	    
	    @Autowired
	    private RegisterRepository applicantRepository;
 
	    public String ApplicantApplyJob(long  applicantId, long jobId) {
	    	
	        Applicant applicant = applicantRepository.findById(applicantId);
	        System.out.println("applicant id"+applicant.getId());
	       Job job = jobRepository.findById(jobId).orElse(null);
	       if(applicant ==null && job == null)
	        	return "Applicant ID or Job Id not found";
	       else
	       {
	      // System.out.println("job is "+job.getId());
	    	   if (!applyJobRepository.existsByApplicantAndJob(applicant, job)) {
	           ApplyJob applyJob = new  ApplyJob();
	           applyJob.setApplicant(applicant);
	           applyJob.setJob(job);
	           applyJobRepository.save(applyJob);
	           return "Job Applied Successfully";
	         }
	    	   else {
	    		   return "Job has already been applied by the applicant";
	            }
	       }
	    }
 
	public List<Job> getAppliedJobsForApplicant(long applicantId) {
		List<Job> result = new ArrayList<>();      
	     
	      try {
	          List<ApplyJob> appliedJobs = applyJobRepository.findByApplicantId(applicantId);
 
	          for (ApplyJob appliedJobs1 : appliedJobs) {
	              result.add(appliedJobs1 .getJob());
	          }
 
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
 
	      return result;
	  }
//	 public List<AppliedApplicantInfo> getAppliedApplicantsForRecruiter(long jobRecruiterId) {
//    return applyJobRepository.findAppliedApplicantsInfo(jobRecruiterId);
//}
 
public List<AppliedApplicantInfoDTO> getAppliedApplicants(long jobRecruiterId) {
List<AppliedApplicantInfo> appliedApplicants = applyJobRepository.findAppliedApplicantsInfo(jobRecruiterId);
 
List<AppliedApplicantInfoDTO> dtoList = new ArrayList<>();
for (AppliedApplicantInfo appliedApplicantInfo : appliedApplicants) {
    AppliedApplicantInfoDTO dto = mapToDTO(appliedApplicantInfo);
    dtoList.add(dto);
}
 
return dtoList;
}
 
 
 
private AppliedApplicantInfoDTO mapToDTO(AppliedApplicantInfo appliedApplicantInfo) {
    AppliedApplicantInfoDTO dto = new AppliedApplicantInfoDTO();
    dto. setApplyjobid(appliedApplicantInfo.getApplyjobid());
    dto.setName(appliedApplicantInfo.getName());
    dto.setEmail(appliedApplicantInfo.getEmail());
    dto.setMobilenumber(appliedApplicantInfo.getMobilenumber());
    dto.setJobTitle(appliedApplicantInfo.getJobTitle());
    dto.setApplicantStatus(appliedApplicantInfo.getApplicantStatus());
    
    dto.setMinimumExperience(appliedApplicantInfo.getMinimumExperience());
    dto.setSkillName(appliedApplicantInfo.getSkillName());
    dto.setLocation(appliedApplicantInfo.getLocation());
    dto.setLocation(appliedApplicantInfo.getLocation());
  
 
    // Map other fields as needed
 
    return dto;
}
 
 
//public String updateApplyJobAndSendAlert(
//        Long applyJobId,
//        String applicantStatus,
//        Date scheduleInterviewDate,
//        String recruiterName
//    ) {
//        ApplyJob applyJob = applyJobRepository.findById(applyJobId)
//            .orElseThrow(() -> new EntityNotFoundException("ApplyJob with ID " + applyJobId + " not found"));
//
//        applyJob.setApplicantStatus(applicantStatus);
//        applyJob.setScheduleInterviewDate(scheduleInterviewDate);
//        applyJob.setRecruiterName(recruiterName);
//
//        applyJobRepository.save(applyJob);
//
//        // Send alert to the applicant and construct the alert message
//        String alertMessage = "This application "+applyJobId+" Status changed to " + applicantStatus;
//        
//        return alertMessage;
//    }
 
 
public String updateApplicantStatus(Long applyJobId, String newStatus) {
    ApplyJob applyJob = applyJobRepository.findById(applyJobId)
            .orElseThrow(() -> new EntityNotFoundException("ApplyJob not found"));
 
    applyJob.setApplicantStatus(newStatus);
    applyJobRepository.save(applyJob);
 
    return "Applicant status updated to: " + newStatus;
}
 
public List<ApplicantJobInterviewDTO> getApplicantJobInterviewInfoForRecruiterAndStatus(
        long recruiterId, String applicantStatus) {
    return scheduleInterviewRepository.getApplicantJobInterviewInfoByRecruiterAndStatus(recruiterId, applicantStatus);
}
 
public long countJobApplicantsByRecruiterId(Long recruiterId) {
    return applyJobRepository.countJobApplicantsByRecruiterId(recruiterId);
}
 
public long countSelectedApplicants() {
    // Use the repository method to count applicants with "selected" status
    return applyJobRepository.countByApplicantStatus("selected");
}
 
public long countShortlistedAndInterviewedApplicants() {
    List<String> desiredStatusList = Arrays.asList("shortlisted", "interviews");
    return applyJobRepository.countByApplicantStatusIn(desiredStatusList);
}
}
	    
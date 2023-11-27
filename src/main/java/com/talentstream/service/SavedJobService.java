package com.talentstream.service;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.talentstream.entity.Applicant;
import com.talentstream.entity.Job;
import com.talentstream.entity.SavedJob;
import com.talentstream.repository.JobRepository;
import com.talentstream.repository.RegisterRepository;
import com.talentstream.repository.SavedJobRepository;

@Service
public class SavedJobService {
	@Autowired
    private SavedJobRepository savedJobRepository;

    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private RegisterRepository applicantRepository;

    public void saveJobForApplicant(long applicantId, long jobId) throws Exception {
        Applicant applicant = applicantRepository.findById(applicantId);
        Job job = jobRepository.findById(jobId).orElse(null);
        if(applicant ==null && job == null)
        	throw new Exception("Applicant ID or Job Id not found");
        
        else {
            if (!savedJobRepository.existsByApplicantAndJob(applicant, job)) {
                SavedJob savedJob = new SavedJob();
                savedJob.setApplicant(applicant);
                savedJob.setJob(job);
                savedJobRepository.save(savedJob);
            } else {
                throw new Exception("Job has already been saved by the applicant");
            }
        }
    }

public List<Job> getSavedJobsForApplicant(long applicantId) {
	List<Job> result = new ArrayList<>();      
     
      try {
          List<SavedJob> savedJobs = savedJobRepository.findByApplicantId(applicantId);

          for (SavedJob savedJob : savedJobs) {
              result.add(savedJob.getJob());
          }

      } catch (Exception e) {
    	  e.printStackTrace();
      }

      return result;
  }

/*public void saveJobForApplicant(long applicantId, long jobId) throws Exception {
Applicant applicant = applicantRepository.findById(applicantId);
Job job = jobRepository.findById(jobId).orElse(null);

if (applicant != null && job != null) {
  
    if (!savedJobRepository.existsByApplicantAndJob(applicant, job)) {
        SavedJob savedJob = new SavedJob();
        savedJob.setApplicant(applicant);
        savedJob.setJob(job);
        savedJobRepository.save(savedJob);
    } else {
      
        throw new Exception("Job has already been saved by the applicant");
    	}
		}
	}*/
}


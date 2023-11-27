package com.talentstream.repository;



import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talentstream.entity.ApplicantSkills;
import com.talentstream.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
	@Query("SELECT DISTINCT j FROM Job j JOIN j.skillsRequired s WHERE s.skillName = :skillName")
    Page<Job> findJobsBySkillName(String skillName, Pageable pageable);
		List<Job> findByJobTitleContainingIgnoreCase(String jobTitle);
	List<Job> findByLocationContainingIgnoreCase(String location);
	List<Job> findByIndustryTypeContainingIgnoreCase(String industryType);
	List<Job> findByEmployeeTypeContainingIgnoreCase(String employeeType);
	List<Job> findByMinimumQualificationContainingIgnoreCase(String minimumQualification);
	List<Job> findBySpecializationContainingIgnoreCase(String specialization);
	List<Job> findBySkillsRequiredSkillName(String skillName);
	List<Job> findBySkillsRequiredSkillNameAndSkillsRequiredMinimumExperience(String skillName, int minimumExperience);
	
	@Query("SELECT DISTINCT j FROM Job j " +
	           "JOIN j.skillsRequired r " +
	           "WHERE LOWER(r.skillName) IN :skillNames")
	 List<Job> findBySkillsRequiredIgnoreCaseAndSkillNameIn(Set<String> skillNames);
	@Query("SELECT j FROM Job j WHERE j.jobRecruiter.id = :jobRecruiterId")
    List<Job> findByJobRecruiterId(@Param("jobRecruiterId") Long jobRecruiterId);
	@Query("SELECT COUNT(j) FROM Job j WHERE j.jobRecruiter.id = :recruiterId")
    long countJobsByRecruiterId(@Param("recruiterId") Long recruiterId);
	
	@Query("SELECT j FROM Job j JOIN j.skillsRequired s WHERE LOWER(s.skillName) LIKE LOWER(CONCAT('%', :skillName, '%'))")
    List<Job> findJobsBySkillNameIgnoreCase(@Param("skillName") String skillName);
	
}

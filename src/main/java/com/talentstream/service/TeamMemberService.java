package com.talentstream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentstream.entity.JobRecruiter;
import com.talentstream.entity.TeamMember;
import com.talentstream.repository.JobRecruiterRepository;
import com.talentstream.repository.TeamMemberRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository; // Assuming you have a TeamMemberRepository

    @Autowired
    private JobRecruiterRepository recruiterRepository; // Assuming you have a RecruiterRepository

    public TeamMember addTeamMemberToRecruiter(Long recruiterId, TeamMember teamMember) {
        JobRecruiter recruiter = recruiterRepository.findById(recruiterId).orElse(null);

        if (recruiter == null) {
            // Handle the case where the recruiter doesn't exist
            return null; // You can return an appropriate response or throw an exception
        }

        teamMember.setRecruiter(recruiter); // Set the recruiter for the team member
        TeamMember savedTeamMember = teamMemberRepository.save(teamMember);

        // You can perform additional logic or return the saved team member
        return savedTeamMember;
    }
    
    public List<TeamMember> getTeammembersByRecruiter(long recruiterId) {
        return teamMemberRepository.findByJobRecruiterId(recruiterId);
    }
    
    public void deleteTeamMemberById(Long teamMemberId) {
        // Check if the Team Member exists
        if (teamMemberRepository.existsById(teamMemberId)) {
            teamMemberRepository.deleteById(teamMemberId);
        } else {
            throw new EntityNotFoundException("Team Member with ID " + teamMemberId + " not found.");
        }
    }
    
    public void resetTeamMemberPassword(Long teamMemberId, String newPassword) {
        // Check if the Team Member exists
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
            .orElseThrow(() -> new EntityNotFoundException("Team Member with ID " + teamMemberId + " not found."));
 
        // Update the password
        teamMember.setPassword(newPassword);
 
        // Save the updated Team Member
        teamMemberRepository.save(teamMember);

    // Other service methods for managing team members
}
}

package com.talentstream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talentstream.entity.TeamMember;
import com.talentstream.service.TeamMemberService;

@RestController
@CrossOrigin("*")
@RequestMapping("/team")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping("/{recruiterId}/team-members")
    public ResponseEntity<TeamMember> addTeamMemberToRecruiter(
        @PathVariable Long recruiterId,
        @RequestBody TeamMember teamMember
    ) {
        TeamMember savedTeamMember = teamMemberService.addTeamMemberToRecruiter(recruiterId, teamMember);

        if (savedTeamMember == null) {
            // Handle the case where the recruiter doesn't exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // You can customize the response or return the saved team member
        return new ResponseEntity<>(savedTeamMember, HttpStatus.CREATED);
    }

    // Other API endpoints for managing team members
    
    @GetMapping("/teammembers/{recruiterId}")
    public List<TeamMember> getTeammembersByRecruiter(@PathVariable("recruiterId") long recruiterId) {
        return  teamMemberService.getTeammembersByRecruiter(recruiterId);
    }
    @DeleteMapping("/{teamMemberId}")
    public ResponseEntity<String> deleteTeamMember(@PathVariable Long teamMemberId) {
        teamMemberService.deleteTeamMemberById(teamMemberId);
        return new ResponseEntity<>("Team Member deleted successfully", HttpStatus.OK);
    }
    
    @PutMapping("/{teamMemberId}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable Long teamMemberId, @RequestParam String newPassword) {
        teamMemberService.resetTeamMemberPassword(teamMemberId, newPassword);
        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }
    
    }



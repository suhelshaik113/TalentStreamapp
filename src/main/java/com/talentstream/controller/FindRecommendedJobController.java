package com.talentstream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentstream.entity.Job;
import com.talentstream.service.FinRecommendedJobService;

@RestController
@RequestMapping("/recommendedjob")
public class FindRecommendedJobController {
	private final FinRecommendedJobService finJobService;

    @Autowired
    public FindRecommendedJobController(FinRecommendedJobService finJobService) {
        this.finJobService = finJobService;
    }

    @GetMapping("/findrecommendedjob/{applicantId}")
    public List<Job> recommendJobsForApplicant(@PathVariable String applicantId) {
    	long applicantid = Long.parseLong(applicantId);
    	return finJobService.findJobsMatchingApplicantSkills(applicantid);
    }
}

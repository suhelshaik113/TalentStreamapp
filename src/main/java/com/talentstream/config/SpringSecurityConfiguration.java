package com.talentstream.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.talentstream.filter.JwtRequestFilter;
import com.talentstream.service.JobRecruiterService;


@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	//@Autowired
	private UserDetailsService myUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
//	 @Autowired
//	    private JobRecruiterService jobRecruiterService;

	//@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder()); 
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable()
		
		.authorizeRequests()
		.antMatchers("/job/recruiterssearchBySkillName","/applyjob/recruiter/{jobRecruiterId}/appliedapplicants","/applyjob/recruiters/scheduleInterview/{applyJobId}","/applyjob/recruiters/applyjob-update-status/{applyJobId}/{newStatus","/applyjob/recruiter/{recruiterId}/interviews/{status}","/applyjob/recruiters/applyjobapplicantscount/{recruiterId}","/applyjob/recruiters/selected/count","/applyjob/recruiters/countShortlistedAndInterviewed","/applyjob/current-date","/companyprofile/recruiters/company-profiles/{jobRecruiterId}","/companyprofile/recruiters/getCompanyProfile/{id}","/job/recruiters/saveJob/{jobRecruiterId}","/job/recruiters/viewJobs/{jobRecruiterId}","/recuriters/viewRecruiters","/job/recruiterssearchByJobTitle","/job/recruiterssearchByLocation","/job/recruiterssearchByIndustryType","/job/recruiterssearchByEmployeeType","/job/searchByMinimumQualification","/job/recruiterssearchBySpecialization","/job/recruiterssearchBySkillNameAndExperience","/team/{recruiterId}/team-members","/team/teammembers/{recruiterId}","/team/{teamMemberId}","/team/{teamMemberId}/reset-password","/job/recruiterscountjobs/{recruiterId}").hasAnyRole("JOBRECRUITER")
		.antMatchers("/applicantprofile/createprofile/{applicantid}","/applicantprofile/getdetails/{applicantid}","/applyjob/applicants/applyjob/{applicantId}/{jobId}","/applyjob/getAppliedJobs/{applicantId}","/applyjob/getScheduleInterviews/applicant/{applicantId}/{applyJobId}","/recommendedjob/findrecommendedjob/{applicantId}","/appicant/viewApplicants","/savedjob/applicants/savejob/{applicantId}/{jobId}","/savedjob/getSavedJobs/{applicantId}","/searchjob/applicant/searchjobbyskillname/{applicantId}/jobs/{skillName}","/viewjob/applicant/viewjob/{jobId}").hasAnyRole("JOBAPPLICANT")
	    .antMatchers("/appicant/signOut","/forgotpassword/recuriterverify-otp","/forgotpassword/recuritersend-otp","/forgotpassword/recuriterreset-password/set-new-password/{email}","/recuriters/saverecruiters","/recuriters/recruiterLogin","/recuriters/registration-send-otp","/appicant/saveApplicant","/appicant/applicantLogin","/appicant/applicantsendotp","/appicant/applicantverify-otp","/appicant/applicantreset-password/{email}","/appicant/appicantsignOut","/appicant/forgotpasswordsendotp").permitAll()
						.anyRequest().authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	// @Override
//	    protected void configure(HttpSecurity httpSecurity) throws Exception {
//	        httpSecurity.csrf().disable()
//	                .authorizeRequests()
//	                    .antMatchers("/authenticate", "/send-otp", "/verify-otp", "/saveRecruiters", "/saveApplicant", "/registration-send-otp", "/applicantLogin").permitAll()
//	                    .anyRequest().authenticated()
//	                .and()
//	                .exceptionHandling()
//	                .and()
//	                .sessionManagement()
//	                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	 .and()
//    .httpBasic().disable()
//    .formLogin().disable();
//
//        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }
}

	
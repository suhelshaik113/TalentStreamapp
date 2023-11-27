package com.talentstream.controller;
import java.util.HashMap;

import java.util.List;

import java.util.Map;

 

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import com.talentstream.entity.Applicant;
import com.talentstream.entity.AuthenticationResponse;
import com.talentstream.entity.Login;

import com.talentstream.entity.NewPasswordRequest;

import com.talentstream.entity.OtpVerificationRequest;
import com.talentstream.response.ResponseHandler;
import com.talentstream.service.EmailService;
import com.talentstream.service.JwtUtil;
import com.talentstream.service.MyUserDetailsService;
import com.talentstream.service.OtpService;

import com.talentstream.service.RegisterService;
import com.talentstream.service.JobRecruiterService;

 
 
@CrossOrigin("*")
@RestController
@RequestMapping("/appicant")
public class RegisterController {
	@Autowired
    MyUserDetailsService myUserDetailsService;
	 @Autowired
	    private OtpService otpService;
		 private Map<String, Boolean> otpVerificationMap = new HashMap<>();

		 @Autowired
			private AuthenticationManager authenticationManager;
		     @Autowired
			private JwtUtil jwtTokenUtil;
		    
	    @Autowired
	    private EmailService emailService;

		@Autowired
	     RegisterService regsiterService;
		@Autowired
		JobRecruiterService recruiterService;	   

	    @Autowired
	    public RegisterController(RegisterService regsiterService)
	    {
	        this.regsiterService = regsiterService;

	    }

 	    @PostMapping("/saveApplicant")
	    public ResponseEntity<String> register(@RequestBody Applicant applicant) {
	       return regsiterService.saveapplicant(applicant);
	    }

     	    @PostMapping("/applicantLogin")
	    public ResponseEntity<Object> login(@RequestBody Login loginRequest) throws Exception {
	    	 Applicant applicant = regsiterService.login(loginRequest.getEmail(), loginRequest.getPassword());
	        System.out.println(loginRequest.getEmail());
	        System.out.println(applicant.getEmail());
	        if (applicant!=null) {
	        	return createAuthenticationToken(loginRequest, applicant);
	        } else {
	            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
	        }
	    }

	    private ResponseEntity<Object> createAuthenticationToken(Login loginRequest,  Applicant applicant ) throws Exception {
				    	try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
				);
			}
			catch (BadCredentialsException e) {
				throw new Exception("Incorrect username or password", e);
			}
	    	 UserDetails userDetails = myUserDetailsService.loadUserByUsername(applicant.getEmail());
	    	System.out.println(userDetails.getUsername());
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			System.out.println(jwt);
			return ResponseHandler.generateResponse("Login successfully"+userDetails.getAuthorities(), HttpStatus.OK, new AuthenticationResponse(jwt),applicant.getEmail(),applicant.getName(),applicant.getId());
		}


	    @PostMapping("/applicantsendotp")
	    public ResponseEntity<String> sendOtp(@RequestBody Applicant  request) {
	        String userEmail = request.getEmail();
	        Applicant applicant = regsiterService.findByEmailAddress(userEmail);
	        if (applicant == null) {     
	            String otp = otpService.generateOtp(userEmail);
	         	            emailService.sendOtpEmail(userEmail, otp);
	 	            otpVerificationMap.put(userEmail, true);
	 	            return ResponseEntity.ok("OTP sent to your email.");
	        }

	        else {
	        	 return ResponseEntity.badRequest().body("Email is already  registered.");
	        }
	    }
	    @PostMapping("/forgotpasswordsendotp")
	    public ResponseEntity<String> ForgotsendOtp(@RequestBody Applicant  request) {
	        String userEmail = request.getEmail();
	        Applicant applicant = regsiterService.findByEmailAddress(userEmail);
	        if (applicant != null) {     
	            String otp = otpService.generateOtp(userEmail);
	         	            emailService.sendOtpEmail(userEmail, otp);
	 	            otpVerificationMap.put(userEmail, true);
	 	            System.out.println(otp);
	 	            return ResponseEntity.ok("OTP sent successfully");
	        }
	        else {
	        	 return ResponseEntity.badRequest().body("Email not found."); 
	        } 
	    }

	    @PostMapping("/applicantverify-otp")
	    public ResponseEntity<String> verifyOtp( @RequestBody  OtpVerificationRequest verificationRequest

	    ) {
	        String otp=verificationRequest.getOtp();
	        String email=verificationRequest.getEmail();
	        System.out.println(otp+email);

	        if (otpService.validateOtp(email, otp)) {
	            return ResponseEntity.ok("OTP verified successfully");
	        } else {
	            return ResponseEntity.badRequest().body("Incorrect OTP or Timeout.");
	        }

	    }

	    @PostMapping("/applicantreset-password/{email}")
	    public ResponseEntity<String> setNewPassword(@RequestBody NewPasswordRequest request,       @PathVariable String email) {
	        String newPassword = request.getNewPassword();
	        String confirmedPassword = request.getConfirmedPassword();
	        if (email == null) {
	            return ResponseEntity.badRequest().body("Email not found.");

	        }
	        Applicant applicant = regsiterService.findByEmailAddress(email);
	        if (applicant == null) {
	            return ResponseEntity.badRequest().body("User not found.");
	        }
	        applicant.setPassword(newPassword);
	     	        regsiterService.saveapplicant(applicant);
	        return ResponseEntity.ok("Password reset was done successfully");
	    }

		@GetMapping("/viewApplicants")

	    public ResponseEntity<List<Applicant>> getAllJobRecruiters() {

	        List<Applicant> applicants = regsiterService.getAllApplicants();

	        return ResponseEntity.ok(applicants);

	    }
		@PostMapping("/appicantsignOut")
	    public ResponseEntity<Void> signOut(@AuthenticationPrincipal Applicant user) {
	    	System.out.println("checking");
	        SecurityContextHolder.clearContext();
	        return ResponseEntity.noContent().build();
	    }
}
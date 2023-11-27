//package com.talentstream.TestControllers;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.test.context.junit4.SpringRunner;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.talentstream.controller.RegisterController;
//import com.talentstream.entity.Applicant;
//import com.talentstream.entity.Login;
//import com.talentstream.service.JwtUtil;
//import com.talentstream.service.OtpService;
//import com.talentstream.service.RegisterService;
//import org.springframework.http.MediaType;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import com.talentstream.service.MyUserDetailsService;
////@WebMvcTest(RegisterController.class)
////@RunWith(SpringRunner.class)
//@ExtendWith(MockitoExtension.class)
//public class RegisterControllerTest {
//	@Autowired
//    private MockMvc mockMvc;
//	//is a testing framework provided by Spring that allows you to perform HTTP requests and simulate interactions with your application's endpoints without actually starting a web server.
//    @Mock   
//    private RegisterService registerService;
//   // The mock object is used to simulate the behavior of RegisterService during the test.
//    @Mock
//    private OtpService otpService;
//    @Mock
//    private AuthenticationManager authenticationManager;
//    @Mock
//    private JwtUtil jwtTokenUtil;
//    @Mock
//    private UserDetailsService userDetailsService;
//    @InjectMocks
//    private RegisterController registerController;
//// it injects the mock RegisterService into the RegisterController
//    
//    @BeforeEach
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
//        //MockMvc instance is set up by configuring it to use the registerController for testing.
//    }
//
//    @Test
//    public void testRegister() throws Exception {
//      
//        Applicant applicant = new Applicant();
//       
//        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Registration successful");
//        Mockito.when(registerService.saveapplicant(applicant)).thenReturn(expectedResponse);
//
//      
//        ObjectMapper objectMapper = new ObjectMapper();
//        //ObjectWriter objectwriter=objectMapper.write();
//        String applicantJson = objectMapper.writeValueAsString(applicant);
//
//     
//        mockMvc.perform(post("/saveApplicant")
//                .content(applicantJson)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//    @Test
//    public void testLogin() throws Exception {
//    	String email = "test@example.com";
//        String password = "testPassword";
//
//        // Mock data for the user retrieved from the service
//        Applicant applicant = new Applicant();
//        applicant.setEmail(email);
//        applicant.setPassword(password);
//
//        // Mock the behavior of the service method
//        Mockito.when(registerService.login(email, password)).thenReturn(applicant);
//
//        // Mock the behavior of the authentication manager (successful authentication)
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
//        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
//
//        // Mock the behavior of the user details service
//        UserDetails userDetails = (UserDetails) new MyUserDetailsService();
//        Mockito.when(userDetailsService.loadUserByUsername(email)).thenReturn((UserDetails) userDetails);
//
//        // Mock JWT generation
//        String mockJwt = "mockJWTToken";
//        Mockito.when(jwtTokenUtil.generateToken(userDetails)).thenReturn(mockJwt);
//
//        // Create the login request JSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        Login loginRequest = new Login();
//        loginRequest.setEmail(email);
//        loginRequest.setPassword(password);
//        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);
//
//        mockMvc.perform(post("/applicantLogin")
//                .content(loginRequestJson)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Login successfully"))
//                .andExpect(jsonPath("$.token").value(mockJwt))
//                .andExpect(jsonPath("$.email").value(email));
//    }
//
//
//    private void setAuthenticationContext(String username) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authRequest);
//        SecurityContextHolder.setContext(context);
//    }
//}
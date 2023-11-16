package com.chubb.expensemanager.controllers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.expensemanager.config.jwt.JwtUtils;
import com.chubb.expensemanager.models.ERole;
import com.chubb.expensemanager.models.Role;
import com.chubb.expensemanager.models.User;
import com.chubb.expensemanager.payloads.reponses.MessageResponse;
import com.chubb.expensemanager.payloads.reponses.UserInfoResponse;
import com.chubb.expensemanager.payloads.requests.LoginRequest;
import com.chubb.expensemanager.payloads.requests.SignupRequest;
import com.chubb.expensemanager.repositories.RoleRepository;
import com.chubb.expensemanager.services.UserService;
import com.chubb.expensemanager.servicesimpl.UserDetailsImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

	    String jwtToken = jwtUtils.generateJwtToken(userDetails);

	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(item -> item.getAuthority()).collect(Collectors.toList());
	    
	    User user = userService.findUserById(userDetails.getId());

	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtToken)
	        .body(new UserInfoResponse(jwtToken, user));
	  }

	  @PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	    if (userService.existsByUsername(signUpRequest.getUsername())) {
	      return ResponseEntity
	          .badRequest()
	          .body(new MessageResponse("Error: Username is already taken!"));
	    }

	    if (userService.existsByEmail(signUpRequest.getEmail())) {
	      return ResponseEntity
	          .badRequest()
	          .body(new MessageResponse("Error: Email is already in use!"));
	    }

	    // Create new user's account
	    User user = new User();
	    BeanUtils.copyProperties(signUpRequest, user);
	    user.setPassword(encoder.encode(signUpRequest.getPassword()));

	    Set<String> strRoles = signUpRequest.getRoles();
	    Set<Role> roles = new HashSet<>();

	    if (strRoles == null) {
	      Role userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
	          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	      roles.add(userRole);
	    } else {
	      strRoles.forEach(role -> {
	        switch (role) {
	        case "admin":
	          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(adminRole);

	          break;
	        case "manager":
	          Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(managerRole);

	          break;
	        default:
	          Role userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(userRole);
	        }
	      });
	    }

	    user.setRoles(roles);
	    userService.saveUser(user);

	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	  }
}

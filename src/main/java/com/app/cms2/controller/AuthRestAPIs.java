package com.app.cms2.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cms2.repository.RoleRepository;
import com.app.cms2.repository.UserRepository;
import com.app.cms2.security.jwt.JwtProvider;

import app.com.cms2.message.request.LoginForm;
import app.com.cms2.message.request.RegisterForm;
import app.com.cms2.message.response.JwtResponse;
import app.com.cms2.message.response.ResponseMessage;
import app.com.cms2.model.Role;
import app.com.cms2.model.RoleName;
import app.com.cms2.model.User;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
 
  @Autowired
  AuthenticationManager authenticationManager;
 
  @Autowired
  UserRepository userRepository;
 
  @Autowired
  RoleRepository roleRepository;
 
  @Autowired
  PasswordEncoder encoder;
 
  @Autowired
  JwtProvider jwtProvider;
 
  @PostMapping("/login")
  public ResponseEntity authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
 
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
 
    SecurityContextHolder.getContext().setAuthentication(authentication);
 
    String jwt = jwtProvider.generateJwtToken(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
 
    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
  }
 
  
  
  @PostMapping("/register")
  public ResponseEntity registerUser(@Valid @RequestBody RegisterForm registerRequest) {
    if (userRepository.existsByUsername(registerRequest.getUsername())) {
      return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
          HttpStatus.BAD_REQUEST);
    }
 
    if (userRepository.existsByEmail(registerRequest.getEmail())) {
      return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
          HttpStatus.BAD_REQUEST);
    }
 
    // Creating user's account
    User user = new User(registerRequest.getName(), registerRequest.getUsername(), registerRequest.getEmail(),
        encoder.encode(registerRequest.getPassword()));
 
    Set<String> strRoles = registerRequest.getRole();
    Set<Role> roles = new HashSet<Role>();
 
    strRoles.forEach(role -> {
      switch (role) {
	    case "ADMIN":
	    	Role admin = roleRepository.findByName(RoleName.ADMIN)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	    	roles.add(admin);
        break;
	    case "ProjectManager":
          Role pm = roleRepository.findByName(RoleName.ProjectManager)
              .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
          	roles.add(pm);
          break;
	    case "LigneManager":
        Role lm = roleRepository.findByName(RoleName.LigneManager)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(lm);
 
        break;
      default:
        Role machiniste = roleRepository.findByName(RoleName.Machinist)
            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(machiniste);
      }
    });
 
    user.setRoles(roles);
    userRepository.save(user);
 
    return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
  }
}

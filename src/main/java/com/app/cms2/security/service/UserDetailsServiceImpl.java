package com.app.cms2.security.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.app.cms2.repository.UserRepository;

import app.com.cms2.model.User;

public class UserDetailsServiceImpl implements UserDetailsService{
	  
	  @Autowired
	  UserRepository userRepository;
	 
	  
	  @Transactional
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	 
	    User user = userRepository.findByUsername(username).orElseThrow(
	        () -> new UsernameNotFoundException("User Not Found with -> username or email : " 
	    + username));
	 
	    return UserPrinciple.build(user);
	  }

}

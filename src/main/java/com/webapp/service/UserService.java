package com.webapp.service;

import java.util.ArrayList;
import java.util.List;

import com.webapp.repository.UserRepository;
import com.webapp.model.Role;
import com.webapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void createUser(User user) {
		baseCreateUser(user, "USER");
	}

	public void createAdmin(User user) {
		baseCreateUser(user, "ADMIN");
	}

	private void baseCreateUser(User user, String role) {
		BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		Role userRole = new Role(role);
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		this.userRepository.save(user);
	}

	public User findByEmail(String email) {
	  return this.userRepository.findByEmail(email);
	}

	public boolean isUserPresent(String email) {
		User u = this.userRepository.findByEmail(email);
		if(u!=null)
			return true;
		
		return false;
	}

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findByName(String name) {
		return  this.userRepository.findByName(name);
	}

	public void deleteByName(String name){
		User user = this.userRepository.findByName(name);
		this.userRepository.delete(user);
	}

	public void deleteByEmail(String email){
		User user = this.userRepository.findByEmail(email);
		this.userRepository.delete(user);
	}
}

package com.susanfu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.susanfu.entity.User;
import com.susanfu.entity.security.Role;
import com.susanfu.entity.security.UserRole;
import com.susanfu.repository.UserRepository;
import com.susanfu.service.UserService;

@RestController
public class RestResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/signup")
	public void signupUser(@RequestBody User user) {
		System.out.println(user);
		Set<UserRole> userRoles = new HashSet<>();
		Role role = new Role();
		role.setName("ROLE_USER");
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login...";
	}
	
	@RequestMapping("/tokenValidation")
	public ResponseEntity tokenValidation(@Param("token") String token, @Param("email") String email) throws IOException{
		HttpStatus httpStatus = userService.validateToken(token);
		if(httpStatus.is2xxSuccessful()) {
			User user = userRepository.findByEmail(email);
			Map<String, String> entity = new HashMap<>();
			entity.put("email", email);
			if(user != null) {
				userService.setUserSession(user.getUsername());
				entity.put("username", user.getUsername());
				return new ResponseEntity<>(entity, httpStatus);
			}else {
				entity.put("username", "");
				return new ResponseEntity<>(entity, httpStatus);
			}
		}
		return new ResponseEntity<>(httpStatus);
	}
}

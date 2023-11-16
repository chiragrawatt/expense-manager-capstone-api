package com.chubb.expensemanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.models.ExpenseCategory;
import com.chubb.expensemanager.models.User;
import com.chubb.expensemanager.services.UserService;
import com.chubb.expensemanager.servicesimpl.UserDetailsImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/validate")
	public ResponseEntity<User> validateUserToken(Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		User userInfo = userService.findUserById(user.getId());

		if (userInfo == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(userInfo));
	}
	
	@GetMapping("")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUser();

		if (users == null || users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(users));
	}
}

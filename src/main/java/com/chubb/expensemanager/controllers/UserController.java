package com.chubb.expensemanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.models.ExpenseCategory;
import com.chubb.expensemanager.models.Notification;
import com.chubb.expensemanager.models.Team;
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

		if (users == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(users));
	}	
		
	@PostMapping("/team")
	public ResponseEntity<List<User>> getUsersByTeam(@RequestBody Team team) {
		List<User> users = userService.findByTeam(team);

		if (users == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(users));
	}

	@GetMapping("/notifications")
	public ResponseEntity<List<Notification>> getNotifications(Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		List<Notification> notifications = userService.getNotifications(user.getId());

		if (notifications == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(notifications));
	}
	
	@PutMapping("/notify/{userId}")
	public ResponseEntity<Integer> addNotification(@RequestBody Notification notification, @PathVariable String userId) {
		notification.setIsVisited(false);
		
		Integer notifications = userService.appendNotification(userId, notification);

		if (notifications == null || notifications == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(notifications));
	}
	
	@GetMapping("/notifications/visit")
	public ResponseEntity<List<Notification>> setNotificationsToVisited(Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		List<Notification> notifications = userService.setNotificationsAsVisited(user.getId());

		if (notifications == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(notifications));
	}
}

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.models.ExpenseCategory;
import com.chubb.expensemanager.services.EventService;
import com.chubb.expensemanager.servicesimpl.UserDetailsImpl;

@RestController
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("/employee")
	public ResponseEntity<List<Event>> geteventByEmployeeId(Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		List<Event> event = eventService.findByParticipantId(user.getId());

		if (event == null || event.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(event));
	}
	
	@GetMapping("/manager")
	public ResponseEntity<List<Event>> geteventByManagerId(Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println(user);
		
		List<Event> events = eventService.findByManagerId(user.getId());

		if (events == null || events.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		System.out.println(events);

		return ResponseEntity.of(Optional.of(events));
	}
	
	@PostMapping("")
	public ResponseEntity<Event> addEvent(@RequestBody Event newEvent,Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println(user);
		
		Event event = eventService.addEvent(newEvent);

		if (event == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(event));
	}
	
	@GetMapping("/{eventId}")
	public ResponseEntity<Event> getEventByid(@PathVariable String eventId, Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println(user);
		
		Event event = eventService.findEventByid(eventId);
				
		if (event == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(event));
	}
}

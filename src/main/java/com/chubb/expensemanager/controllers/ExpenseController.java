package com.chubb.expensemanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.models.Expense;
import com.chubb.expensemanager.models.User;
import com.chubb.expensemanager.services.ExpenseService;
import com.chubb.expensemanager.servicesimpl.UserDetailsImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/expense")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@PostMapping("")
	public ResponseEntity<Expense> addExpense(@RequestBody Expense newExpense, Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		User creator = new User();
		creator.setId(user.getId());
		
		newExpense.setCreator(creator);
		
		Expense expense = expenseService.saveExpense(newExpense);

		if (expense == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(expense));
	}
	
	@GetMapping("")
	public ResponseEntity<List<Expense>> getExpenses(Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		List<Expense> expenses = expenseService.getExpensesByUserId(user.getId());
		
		System.out.println(expenses);

		if (expenses == null || expenses.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(expenses));
	}
}


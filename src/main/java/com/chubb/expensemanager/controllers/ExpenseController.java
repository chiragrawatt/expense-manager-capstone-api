package com.chubb.expensemanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.models.Expense;
import com.chubb.expensemanager.models.ExpenseStatus;
import com.chubb.expensemanager.models.User;
import com.chubb.expensemanager.services.EventService;
import com.chubb.expensemanager.services.ExpenseService;
import com.chubb.expensemanager.servicesimpl.UserDetailsImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/expense")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private EventService eventService;
	
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
	
	@GetMapping("/{expenseId}")
	public ResponseEntity<Expense> getExpenseById(@PathVariable String expenseId) {
		Expense expense = expenseService.getExpenseById(expenseId);

		if (expense == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(expense));
	}
	
	@GetMapping("")
	public ResponseEntity<List<Expense>> getExpensesByCreatorId(Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		List<Expense> expenses = expenseService.getExpensesByUserId(user.getId());
		
		System.out.println(expenses);

		if (expenses == null || expenses.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(expenses));
	}
	
	@DeleteMapping("/{expenseId}")
    public Integer deleteExpense(@PathVariable String expenseId) {
        return expenseService.deleteExpenseById(expenseId);
    }
	
//	@GetMapping("/pending/{event}")
//    public ResponseEntity<List<Expense>> getPendingExpensesForEvent(@PathVariable String event) {
//        try {
//            List<Expense> pendingExpenses = expenseService.getPendingExpensesForEvent(event);
//            return new ResponseEntity<>(pendingExpenses, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
	
	@GetMapping("/manager/{expenseStatus}")
    public ResponseEntity<List<Expense>> getPendingExpensesForManager(@PathVariable ExpenseStatus expenseStatus, Authentication authentication) {
		UserDetailsImpl manager = (UserDetailsImpl) authentication.getPrincipal();
	
        try {
            List<Expense> pendingExpenses = expenseService.getExpensesByStatusAndManager(manager.getId(), expenseStatus);
            return new ResponseEntity<>(pendingExpenses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PutMapping("/status/{expenseId}")
	public ResponseEntity<Expense> changeExpenseStatus(@RequestBody ExpenseStatus expenseStatus, @PathVariable String expenseId) {
		Expense expense = expenseService.changeExpenseStatus(expenseId, expenseStatus);

		if (expense == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		if(expenseStatus == ExpenseStatus.APPROVED) {
			eventService.updateParticipantRemainingBudget(expense.getEvent().getId(), expense.getCreator().getId(), expense.getAmount());
		}
		
		return ResponseEntity.of(Optional.of(expense));
	}
}


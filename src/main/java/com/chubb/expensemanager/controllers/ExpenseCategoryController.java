package com.chubb.expensemanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.expensemanager.models.ExpenseCategory;
import com.chubb.expensemanager.services.ExpenseCategoryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/expense/categories")
public class ExpenseCategoryController {

	@Autowired
	private ExpenseCategoryService expenseCategoryService;

	@GetMapping("")
	public ResponseEntity<List<ExpenseCategory>> getAllCategories() {
		List<ExpenseCategory> categories = expenseCategoryService.getAllExpenseCategories();

		if (categories == null || categories.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(categories));
	}

	@PostMapping("")
	public ResponseEntity<String> insertCategories(@RequestBody List<ExpenseCategory> categories) {
		boolean inserted = expenseCategoryService.insertCategories(categories);

		if (inserted == false) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().body("Categories inserted successfully");
	}

}

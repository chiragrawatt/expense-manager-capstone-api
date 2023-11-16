package com.chubb.expensemanager.services;

import java.util.List;

import com.chubb.expensemanager.models.Expense;

public interface ExpenseService {
	
	public Expense getExpenseById(String expenseId);
	
	public List<Expense> getExpensesByUserId(String userId);
	
	public Expense saveExpense(Expense expense);
}

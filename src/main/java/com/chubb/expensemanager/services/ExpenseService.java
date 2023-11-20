package com.chubb.expensemanager.services;

import java.util.List;
import java.util.Optional;

import com.chubb.expensemanager.models.Expense;
import com.chubb.expensemanager.models.ExpenseStatus;

public interface ExpenseService {
	
	public Expense getExpenseById(String expenseId);
	
	public List<Expense> getExpensesByUserId(String userId);
	
	public Expense saveExpense(Expense expense);

	Integer deleteExpenseById(String expenseId);
	
	List<Expense> getExpensesByStatusAndManager(String managerId, ExpenseStatus status);

	Expense changeExpenseStatus(String expenseId, ExpenseStatus status);
}

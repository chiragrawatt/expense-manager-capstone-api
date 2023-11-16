package com.chubb.expensemanager.services;

import java.util.List;

import com.chubb.expensemanager.models.ExpenseCategory;

public interface ExpenseCategoryService {
	
	public List<ExpenseCategory> getAllExpenseCategories();
	
	public boolean insertCategories(List<ExpenseCategory> categories);
}

package com.chubb.expensemanager.servicesimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.expensemanager.models.ExpenseCategory;
import com.chubb.expensemanager.repositories.ExpenseCategoryRepository;
import com.chubb.expensemanager.services.ExpenseCategoryService;

@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {
	
	@Autowired
	private ExpenseCategoryRepository expenseCategoryRepository;
	private static final Logger logger = LoggerFactory.getLogger(ExpenseCategoryServiceImpl.class);

	@Override
	public List<ExpenseCategory> getAllExpenseCategories() {
		List<ExpenseCategory> categories = null;
		
		try {
			categories = expenseCategoryRepository.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return categories;
	}

	@Override
	public boolean insertCategories(List<ExpenseCategory> categories) {
		boolean inserted = false;
		
		try {
			expenseCategoryRepository.insert(categories);
			inserted = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return inserted;
	}
	
	
	
}

package com.chubb.expensemanager.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.models.Expense;
import com.chubb.expensemanager.repositories.ExpenseRepository;
import com.chubb.expensemanager.services.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

	@Override
	public Expense getExpenseById(String expenseId) {
		Expense event = null;
		System.out.println(expenseId);

		try {
			event = expenseRepository.findById(expenseId).get();
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return event;
	}

	@Override
	public List<Expense> getExpensesByUserId(String userId) {
		List<Expense> expenses = null;
		System.out.println("userId");
		System.out.println(userId);

		try {
			expenses = expenseRepository.findAll();
			expenses.stream().forEach(expense -> expense.getEvent());
			expenses = expenses.stream()
					.filter(expense -> expense.getCreator().getId().equals(userId))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return expenses;
	}

	@Override
	public Expense saveExpense(Expense newExpense) {
		Expense expense = null;

		try {
			expense = expenseRepository.save(newExpense);
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return expense;
	}

}

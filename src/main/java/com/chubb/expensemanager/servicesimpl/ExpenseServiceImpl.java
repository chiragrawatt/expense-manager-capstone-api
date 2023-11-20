package com.chubb.expensemanager.servicesimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.models.Expense;
import com.chubb.expensemanager.models.ExpenseStatus;
import com.chubb.expensemanager.models.User;
import com.chubb.expensemanager.repositories.EventRepository;
import com.chubb.expensemanager.repositories.ExpenseRepository;
import com.chubb.expensemanager.services.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private EventRepository eventRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

	@Override
	public Expense getExpenseById(String expenseId) {
		Expense expense = null;
		System.out.println(expenseId);

		try {
			expense = expenseRepository.findById(expenseId).get();
			System.out.println(expense.getReceipt());
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		return expense;
	}

	@Override
	public List<Expense> getExpensesByUserId(String userId) {
		List<Expense> expenses = null;

		try {
			expenses = expenseRepository.findByCreatorId(userId);
			System.out.println(expenses);
			System.out.println(expenses);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return expenses;
	}

	@Override
	public Expense saveExpense(Expense newExpense) {
		Expense expense = null;

		try {
			expense = expenseRepository.save(newExpense);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return expense;
	}

	@Override
	public Integer deleteExpenseById(String expenseId) {
		try {
			System.out.println(expenseId);
			expenseRepository.deleteById(expenseId);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Expense> getExpensesByStatusAndManager(String managerId, ExpenseStatus status) {
		try {
			List<Expense> pendingExpenses = expenseRepository.findByStatus(status);
			
			pendingExpenses = pendingExpenses.stream()
					.filter(exp -> exp.getEvent().getCreator().getId().equals(managerId))
					.collect(Collectors.toList());
			System.out.println(pendingExpenses);
			return pendingExpenses;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to get pending expenses for manager with id: " + managerId);
		}
	}
	
	@Override
	public Expense changeExpenseStatus(String expenseId, ExpenseStatus status) {
		Expense expense = null;

		try {
			expense = expenseRepository.findById(expenseId).get();
			expense.setStatus(status);
			expense = expenseRepository.save(expense);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return expense;
	}

}

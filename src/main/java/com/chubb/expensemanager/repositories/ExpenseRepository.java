package com.chubb.expensemanager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.chubb.expensemanager.models.Expense;
import com.chubb.expensemanager.models.ExpenseStatus;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
	
	@Query(fields = "{'receipt' : 0}")
	List<Expense> findByCreatorId(String userId);
	
	@Query(fields = "{'receipt' : 0}")
	List<Expense> findByStatus(ExpenseStatus status);
}

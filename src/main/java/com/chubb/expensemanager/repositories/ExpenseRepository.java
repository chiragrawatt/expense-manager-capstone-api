package com.chubb.expensemanager.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.chubb.expensemanager.models.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
	
	@Query("{ 'creator.id' : ObjectId('?0') }")
	List<Expense> findByUserId(String userId);
}

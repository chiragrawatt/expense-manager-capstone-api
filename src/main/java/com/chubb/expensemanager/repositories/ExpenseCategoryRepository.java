package com.chubb.expensemanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chubb.expensemanager.models.ExpenseCategory;

public interface ExpenseCategoryRepository extends MongoRepository<ExpenseCategory, String> {

}

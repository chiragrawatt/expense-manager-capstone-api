package com.chubb.expensemanager.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "expense_categories")
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategory {
	private String id;
	private String category;
}

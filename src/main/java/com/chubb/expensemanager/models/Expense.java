package com.chubb.expensemanager.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "expenses")
public class Expense {
	@Id
	private String id;
	private Double amount;
	private ExpenseCategory category;
	private String date;

	private String receipt;
	
	private String description;
	private ExpenseStatus status;
	private String rejectionMessage;
	
	@DocumentReference(lazy = true)
	private User creator;
	
	@DocumentReference
	private Event event;
}

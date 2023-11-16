package com.chubb.expensemanager.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	@Id
	private String id;
	
	@NotBlank
	private String username;

	@Email
	@NotBlank
	private String email;
	
	@DocumentReference(lazy = true)
	private Team team;
	
	@NotBlank
	@JsonIgnore
	private String password;

	@DBRef
	private Set<Role> roles = new HashSet<>();
}

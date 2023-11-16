package com.chubb.expensemanager.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chubb.expensemanager.models.User;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String name);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}

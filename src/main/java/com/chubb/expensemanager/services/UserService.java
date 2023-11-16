package com.chubb.expensemanager.services;

import java.util.List;
import java.util.Optional;

import com.chubb.expensemanager.models.User;

public interface UserService {

	public User saveUser(User user);
	
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String name);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	public List<User> getAllUser();
	
	public User findUserById(String userId);
}

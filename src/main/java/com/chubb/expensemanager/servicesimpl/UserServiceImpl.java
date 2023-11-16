package com.chubb.expensemanager.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.expensemanager.models.User;
import com.chubb.expensemanager.repositories.UserRepository;
import com.chubb.expensemanager.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(ExpenseCategoryServiceImpl.class);

	@Override
	public User saveUser(User user) {
		
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findByUsername(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(String userId) {
		User user = null;
		try {
			user = userRepository.findById(userId).get();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return user;
	}
	
}

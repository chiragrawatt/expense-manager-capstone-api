package com.chubb.expensemanager.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chubb.expensemanager.models.ERole;
import com.chubb.expensemanager.models.Role;

public interface RoleRepository extends MongoRepository<Role, String>{
	Optional<Role> findByName(ERole name);
}

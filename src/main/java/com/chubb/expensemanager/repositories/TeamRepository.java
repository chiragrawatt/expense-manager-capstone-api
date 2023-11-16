package com.chubb.expensemanager.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.chubb.expensemanager.models.Team;
import com.chubb.expensemanager.models.User;

public interface TeamRepository extends MongoRepository<Team, String> {
	
}

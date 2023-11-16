package com.chubb.expensemanager.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.chubb.expensemanager.models.Event;

public interface EventRepository extends MongoRepository<Event, String>{
	
	@Query(value="{ 'creator.id' : ObjectId('?0')}")
	public List<Event> findByManagerId(String managerId);
}

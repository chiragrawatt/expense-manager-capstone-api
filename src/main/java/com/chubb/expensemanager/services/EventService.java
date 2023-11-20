package com.chubb.expensemanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;

import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.repositories.EventRepository;

public interface EventService {

	public List<Event> getAllEvents();

	public List<Event> findByManagerId(String managerId);

	public List<Event> findByParticipantId(String employeeId);

	public Event addEvent(Event newEvent);
	
	public Event findEventByid(String eventId);

	List<Event> getEventsByIsIndividualAndIsActive(Boolean isIndividual, Boolean isActive);

	Integer addEvents(List<Event> events);

	Integer updateParticipantRemainingBudget(String eventId, String participantId, Double expenseAmount);
}

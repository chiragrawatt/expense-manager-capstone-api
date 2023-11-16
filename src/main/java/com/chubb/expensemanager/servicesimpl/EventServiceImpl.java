package com.chubb.expensemanager.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.expensemanager.models.Employee;
import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.repositories.EventRepository;
import com.chubb.expensemanager.services.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;
	private static final Logger logger = LoggerFactory.getLogger(ExpenseCategoryServiceImpl.class);

	@Override
	public Event addEvent(Event newEvent) {
		Event added = null;

		try {
			added = eventRepository.save(newEvent);
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return added;
	}

	@Override
	public List<Event> getAllEvents() {
		List<Event> events = null;

		try {
			events = eventRepository.findAll();
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return events;
	}

	@Override
	public List<Event> findByManagerId(String managerId) {
		List<Event> events = null;
		System.out.println(managerId);

		try {
			events = eventRepository.findByManagerId(managerId);
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return events;
	}

	@Override
	public List<Event> findByParticipantId(String userId) {
		List<Event> events = null;

		try {
			events = eventRepository.findAll();
			events = events.stream().filter(event -> event.getParticipants().stream()
		            .anyMatch(participant -> participant.getId().equals(userId)))
			        .collect(Collectors.toList());
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return events;
	}

	@Override
	public Event findEventByid(String eventId) {
		Event event = null;
		System.out.println(eventId);

		try {
			event = eventRepository.findById(eventId).get();
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return event;
	}

}

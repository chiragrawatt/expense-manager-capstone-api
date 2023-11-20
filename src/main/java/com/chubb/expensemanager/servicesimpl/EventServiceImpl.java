package com.chubb.expensemanager.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.expensemanager.models.Employee;
import com.chubb.expensemanager.models.Event;
import com.chubb.expensemanager.models.EventParticipant;
import com.chubb.expensemanager.repositories.EventRepository;
import com.chubb.expensemanager.services.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public Event addEvent(Event newEvent) {
		Event added = null;

		try {
			added = eventRepository.save(newEvent);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return added;
	}

	@Override
	public List<Event> getAllEvents() {
		List<Event> events = null;

		try {
			events = eventRepository.findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
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
			System.out.println(e.toString());
		}

		return events;
	}

	@Override
	public List<Event> findByParticipantId(String userId) {
		List<Event> events = null;

		try {
			events = eventRepository.findAll();
			events = events.stream().filter(event -> event.getParticipants().stream()
		            .anyMatch(participant -> participant.getUser().getId().equals(userId)))
			        .collect(Collectors.toList());
		} catch (Exception e) {
			System.out.println(e.toString());
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
			System.out.println(e.toString());
		}

		return event;
	}
	
	@Override
    public List<Event> getEventsByIsIndividualAndIsActive(Boolean isIndividual, Boolean isActive) {
		List<Event> events = null;

		try {
			events = eventRepository.findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return events;
    }
	
	@Override
	public Integer addEvents(List<Event> events) {
		Integer added = null;

		try {
			added = eventRepository.saveAll(events).size();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return added;
	}
	
	@Override
	public Integer updateParticipantRemainingBudget(String eventId, String userId, Double expenseAmount) {
 		Integer updated = null;

		try {
			Event event = eventRepository.findById(eventId).get();
			List<EventParticipant> participants = event.getParticipants().stream().map(participant -> {
				if(participant.getUser().getId().equals(userId)) {
					participant.setSpent(participant.getSpent() + expenseAmount);
					System.out.println(participant);
				}
				return participant;
			}).collect(Collectors.toList());
			event.setParticipants(participants);
			eventRepository.save(event);
			updated = 1;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return updated;
	}
}

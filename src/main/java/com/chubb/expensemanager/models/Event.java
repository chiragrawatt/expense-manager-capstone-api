package com.chubb.expensemanager.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
	@Id
	private String id;
	private String title;
	private String description;
	private Long totalBudget;
	private String startDate;
	private String endDate;
	private Boolean isActive;
	private Boolean isIndividual;
	
	@DocumentReference
	private User creator;
	
	private List<EventParticipant> participants;
}

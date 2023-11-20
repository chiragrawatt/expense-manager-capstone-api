package com.chubb.expensemanager.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
	private String navLink;
	private ENotificationType type;
	private String message;
	private Boolean isVisited;
}

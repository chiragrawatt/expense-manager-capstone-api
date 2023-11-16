package com.chubb.expensemanager.services;

import java.util.List;

import com.chubb.expensemanager.models.Team;
import com.chubb.expensemanager.models.User;

public interface TeamService {
	
	public List<Team> getAllTeams();
	
	public Team addTeam(Team team);
}

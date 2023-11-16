package com.chubb.expensemanager.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.expensemanager.models.Team;
import com.chubb.expensemanager.models.User;
import com.chubb.expensemanager.repositories.TeamRepository;
import com.chubb.expensemanager.services.TeamService;

@Service
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	private TeamRepository teamRepository;

	@Override
	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}

	@Override
	public Team addTeam(Team newTeam) {
		return teamRepository.save(newTeam);
	}
}

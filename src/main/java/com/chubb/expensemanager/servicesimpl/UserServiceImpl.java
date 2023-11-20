package com.chubb.expensemanager.servicesimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.management.AttributeChangeNotificationFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.expensemanager.models.EventParticipant;
import com.chubb.expensemanager.models.Notification;
import com.chubb.expensemanager.models.Team;
import com.chubb.expensemanager.models.User;
import com.chubb.expensemanager.repositories.UserRepository;
import com.chubb.expensemanager.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {
			return userRepository.save(user);	
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findByUsername(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(String userId) {
		User user = null;
		try {
			user = userRepository.findById(userId).get();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return user;
	}

	@Override
	public List<User> findByTeam(Team team) {
		List<User> users = null;

		try {
			users = userRepository.findByTeam(team);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return users;
	}

	@Override
	public List<Notification> getNotifications(String userId) {
		List<Notification> notifications = null;

		try {
			notifications = userRepository.findById(userId).get().getNotifications();
			notifications = notifications.stream()
					.collect(Collectors.toList());
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return notifications;
	}
	
	@Override
	public Integer appendNotification(String userId, Notification notification) {
		Integer added = 0;

		try {
			User user = userRepository.findById(userId).get();
			List<Notification> notifications = user.getNotifications();
			if(notifications==null) {
				notifications = new ArrayList<Notification>();
			}
			notifications.add(0, notification);
			user.setNotifications(notifications);
			System.out.println(user);
			userRepository.save(user);
			added = 1;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return added;
	}
	
	
	@Override
	public List<Notification> setNotificationsAsVisited(String userId) {
		List<Notification> notifications = null;

		try {
			User user = userRepository.findById(userId).get();
			notifications = user.getNotifications().stream().map(notification -> {
				notification.setIsVisited(true);
				return notification;
			}).collect(Collectors.toList());
			user.setNotifications(notifications);
			System.out.println(user);
			user = userRepository.save(user);
			notifications = user.getNotifications();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return notifications;
	}

}

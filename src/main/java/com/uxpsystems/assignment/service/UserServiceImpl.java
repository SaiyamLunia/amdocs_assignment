package com.uxpsystems.assignment.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uxpsystems.assignment.dao.UserDAO;
import com.uxpsystems.assignment.exception.UserExistException;
import com.uxpsystems.assignment.exception.UserNotFoundException;
import com.uxpsystems.assignment.model.User;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDao;

	@Override
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public User addUser(User user) {
		LOGGER.info("Inside method: UserServiceImpl.addUser");
		Optional<User> userOpt = userDao.findByUserName(user.getUsername());
		if (!userOpt.isPresent()) {
			user.setStatus("Activated");
			return userDao.save(user);
		} else {
			LOGGER.error("User already exist");
			throw new UserExistException("User already exist.");
		}
	}

	@Override
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public User getUser(String username) {
		LOGGER.info("Inside method: UserServiceImpl.getUser");
		Optional<User> user = userDao.findByUserName(username);
		if (user.isPresent()) {
			LOGGER.info("User found for ID {}: {}", username, user.get());
			return user.get();
		} else {
			LOGGER.error("User not found for ID {}", username);
			throw new UserNotFoundException("UserID not found");
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public User editUser(String username, User user) {
		LOGGER.info("Inside method: UserServiceImpl.editUser");
		Optional<User> userOpt = userDao.findByUserName(user.getUsername());
		if (userOpt.isPresent()) {
			if (!user.getUsername().equalsIgnoreCase(username)) {
				LOGGER.error("User found for ID {}: {}", user.getUsername(), userOpt.get());
				throw new UserExistException("New UserID is already taken.");
			} else {
				userDao.deleteById(userOpt.get().getId());
			}
		}
		user.setStatus("Activated");
		user = userDao.save(user);
		LOGGER.info("User Updated Successfully");
		return user;
	}

	@Override
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(String username) {
		LOGGER.info("Inside method: UserServiceImpl.deleteUser");
		Optional<User> user = userDao.findByUserName(username);
		if (user.isPresent()) {
			LOGGER.info("User found for ID {}: {}", username, user.get());
			userDao.softdelete(user.get().getId());
			LOGGER.info("User deleted successfully", username, user.get());
		} else {
			LOGGER.error("User not found for ID {}", username);
			throw new UserNotFoundException("UserID not found");
		}
	}

}

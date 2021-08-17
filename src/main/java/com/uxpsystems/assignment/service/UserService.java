package com.uxpsystems.assignment.service;

import com.uxpsystems.assignment.model.User;

public interface UserService {

	User addUser(User user);

	User getUser(String username);

	User editUser(String username, User user);

	void deleteUser(String username);

}

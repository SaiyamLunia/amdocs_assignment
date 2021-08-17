package com.uxpsystems.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.uxpsystems.assignment.model.User;
import com.uxpsystems.assignment.model.UserResource;
import com.uxpsystems.assignment.service.UserService;

@RestController
@RequestMapping(path = "/assignment", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/{username}")
	@JsonView(UserResource.UserView.class)
	public ResponseEntity<Object> getUser(@PathVariable String username) {
		return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
	}
	
	@PostMapping("/user")
	@JsonView(UserResource.CreateUserView.class)
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
	}
	
	@PutMapping("/user/{username}")
	@JsonView(UserResource.UserView.class)
	public ResponseEntity<Object> createUser(@PathVariable String username, @RequestBody User user) {
		return new ResponseEntity<>(userService.editUser(username, user), HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{username}")
	@JsonView(UserResource.UserView.class)
	public ResponseEntity<Object> deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
	}
}

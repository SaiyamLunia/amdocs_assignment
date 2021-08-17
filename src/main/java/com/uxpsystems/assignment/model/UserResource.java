package com.uxpsystems.assignment.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

public interface UserResource {

	@JsonView({ CreateUserView.class, UserView.class })
	Long getId();

	void setId(Long id);

	@NotEmpty
	@Size(max = 50)
	@JsonView({ CreateUserView.class, UserView.class })
	String getUsername();

	void setUsername(String username);

	@NotEmpty
	@Size(max = 50)
	@JsonView(CreateUserView.class)
	String getPassword();

	void setPassword(String password);

	@NotEmpty
	@JsonView({ CreateUserView.class, UserView.class })
	String getStatus();

	void setStatus(String status);

	interface CreateUserView {
	}

	interface UserView {
	}
}

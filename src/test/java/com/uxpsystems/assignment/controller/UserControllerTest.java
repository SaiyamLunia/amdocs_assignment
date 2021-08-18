package com.uxpsystems.assignment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uxpsystems.assignment.exception.ExceptionController;
import com.uxpsystems.assignment.exception.UserExistException;
import com.uxpsystems.assignment.exception.UserNotFoundException;
import com.uxpsystems.assignment.model.User;
import com.uxpsystems.assignment.service.UserService;

/**
 * @author slunia
 *
 */
@SpringBootTest
public class UserControllerTest {
	
	private UserController userController;
	
	@Mock
	private UserService userService;

	private MockMvc mvc;
	
	private ObjectMapper objectMapper;
	private User user1, user1Exp;
	String jsonString;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.userController = new UserController();
		this.mvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new ExceptionController()).build();
		objectMapper = new ObjectMapper();
		
		ReflectionTestUtils.setField(userController, "userService", userService);
		
		this.user1 = new User(null, "Jill", "pass12", null);
		this.user1Exp = new User(1L, "Jill", "pass12", "Activated");
		jsonString = objectMapper.writeValueAsString(user1);
		
	}

	/**
	 * Test method for {@link com.uxpsystems.assignment.controller.UserController#getUser(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testGetUser() throws Exception {
		when(userService.getUser(user1Exp.getUsername())).thenReturn(user1Exp);
		mvc.perform(get("/assignment/user/{username}", user1Exp.getUsername())
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.username", is("Jill")))
			      .andExpect(jsonPath("$.status", is("Activated")));
	}
	
	@Test
	public final void testGetUserException() throws Exception {
		when(userService.getUser(user1Exp.getUsername())).thenThrow(new UserNotFoundException("UserID not found"));
		mvc.perform(get("/assignment/user/{username}", user1Exp.getUsername())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is("404")))
				.andExpect(jsonPath("$.message", is("UserID not found")));
	}

	/**
	 * Test method for {@link com.uxpsystems.assignment.controller.UserController#createUser(com.uxpsystems.assignment.model.User)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreateUser() throws Exception {
		when(userService.addUser(any())).thenReturn(user1Exp);
		mvc.perform(post("/assignment/user")
			      .contentType(MediaType.APPLICATION_JSON).content(jsonString))
			      .andExpect(status().isCreated())
			      .andExpect(jsonPath("$.username", is("Jill")))
			      .andExpect(jsonPath("$.status", is("Activated")));
	}

	/**
	 * Test method for {@link com.uxpsystems.assignment.controller.UserController#editUser(java.lang.String, com.uxpsystems.assignment.model.User)}.
	 * @throws Exception 
	 */
	@Test
	public final void testEditUser() throws Exception {
		when(userService.editUser(anyString(), any())).thenReturn(user1Exp);
		mvc.perform(put("/assignment/user/{username}", user1.getUsername())
			      .contentType(MediaType.APPLICATION_JSON).content(jsonString))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.username", is("Jill")))
			      .andExpect(jsonPath("$.status", is("Activated")));
	}
	
	@Test
	public final void testEditUserException() throws Exception {
		when(userService.editUser(anyString(), any())).thenThrow(new UserExistException("New UserID is already taken."));
		mvc.perform(put("/assignment/user/{username}", user1.getUsername())
				.contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("400")))
				.andExpect(jsonPath("$.message", is("New UserID is already taken.")));
	}

	/**
	 * Test method for {@link com.uxpsystems.assignment.controller.UserController#deleteUser(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteUser() throws Exception {
		doNothing().when(userService).deleteUser(user1.getUsername());
		mvc.perform(delete("/assignment/user/{username}", user1.getUsername())
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().string("User deleted successfully!"));
	}

}

package com.uxpsystems.assignment.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.uxpsystems.assignment.dao.UserDAO;
import com.uxpsystems.assignment.exception.UserExistException;
import com.uxpsystems.assignment.exception.UserNotFoundException;
import com.uxpsystems.assignment.model.User;

/**
 * @author slunia
 *
 */
@SpringBootTest
public class UserServiceTest {
	
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private UserDAO userDao;
	
	private User user1, user1Exp, user2;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		this.userServiceImpl = new UserServiceImpl();
		
		ReflectionTestUtils.setField(userServiceImpl, "userDao", userDao);
		
		this.user1 = new User(null, "Jill", "pass12", null);
		this.user1Exp = new User(1L, "Jill", "pass12", "Activated");
		this.user2 = new User(2L, "Jonny", "pass1", "Activated");
		
		when(this.userDao.findByUserName(user2.getUsername())).thenReturn(Optional.of(user2));
	}
	
	/**
	 * Test method for {@link com.uxpsystems.assignment.service.UserServiceImpl#addUser(com.uxpsystems.assignment.model.User)}.
	 */
	@Test
	public void testAddUser() {
		user1.setStatus("Activated");
		when(this.userDao.findByUserName(user1.getUsername())).thenReturn(Optional.empty());
		when(this.userDao.save(user1)).thenReturn(user1Exp);
		
		User user = userServiceImpl.addUser(user1);
		assertEquals(user, user1Exp);
		
	}

	@Test
	public void testAddUserException() {
		when(this.userDao.findByUserName(user1.getUsername())).thenReturn(Optional.of(user1Exp));
		
		try {
			userServiceImpl.addUser(user1);
		} catch (UserExistException e) {
			assertEquals("User already exist.", e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link com.uxpsystems.assignment.service.UserServiceImpl#getUser(java.lang.String)}.
	 */
	@Test
	public void testGetUser() {
		User user = userServiceImpl.getUser(user2.getUsername());
		assertEquals(user, user2);
	}
	
	@Test
	public void testGetUserException() {
		when(this.userDao.findByUserName(user2.getUsername())).thenReturn(Optional.empty());
		try {
			userServiceImpl.getUser(user2.getUsername());
		} catch (UserNotFoundException e) {
			assertEquals("UserID not found", e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.uxpsystems.assignment.service.UserServiceImpl#editUser(java.lang.String, com.uxpsystems.assignment.model.User)}.
	 */
	@Test
	public void testEditUser() {
		doNothing().when(userDao).deleteById(user2.getId());
		when(this.userDao.save(user2)).thenReturn(user2);
		
		User user = userServiceImpl.editUser(user2.getUsername(), user2);
		assertEquals(user, user2);
	}
	
	@Test
	public void testEditUserException() {
		try {
			userServiceImpl.editUser("fakeUsername", user2);
		} catch (UserExistException e) {
			assertEquals("New UserID is already taken.", e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.uxpsystems.assignment.service.UserServiceImpl#deleteUser(java.lang.String)}.
	 */
	@Test
	public void testDeleteUser() {
		userServiceImpl.deleteUser(user2.getUsername());
		verify(userDao).softdelete(user2.getId());
	}
	
	@Test
	public void testDeleteUserException() {
		when(this.userDao.findByUserName(user2.getUsername())).thenReturn(Optional.empty());
		try {
			userServiceImpl.deleteUser(user2.getUsername());
		} catch (UserNotFoundException e) {
			assertEquals("UserID not found", e.getMessage());
		}
	}

}

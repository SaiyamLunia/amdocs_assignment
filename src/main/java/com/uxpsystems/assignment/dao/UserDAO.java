package com.uxpsystems.assignment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uxpsystems.assignment.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long>{

	@Modifying
	@Query("Update User u set u.status='Deactivated' where u.id = :id")
	void softdelete(@Param(value ="id") Long id);

	@Query("Select u from User u where u.username= :username and u.status= 'Activated'")
	Optional<User> findByUserName(@Param(value ="username") String username);

}

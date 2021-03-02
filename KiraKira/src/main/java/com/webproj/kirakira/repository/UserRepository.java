package com.webproj.kirakira.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproj.kirakira.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	public User findByphoneNumber(String phoneNumber);
	public User findByid(UUID id);
	
}

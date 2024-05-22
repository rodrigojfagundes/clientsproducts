package io.github.rodrigojfagundes.clientsproducts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import io.github.rodrigojfagundes.clientsproducts.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	UserDetails findByLogin(String login);
	
}

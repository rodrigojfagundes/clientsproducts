package io.github.rodrigojfagundes.clientsproducts.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.rodrigojfagundes.clientsproducts.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("SELECT obj FROM Client obj")
	Page<Client> find(Pageable pageable);
	
}

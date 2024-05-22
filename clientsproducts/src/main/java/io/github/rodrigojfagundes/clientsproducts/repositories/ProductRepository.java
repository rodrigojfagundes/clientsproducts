package io.github.rodrigojfagundes.clientsproducts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.rodrigojfagundes.clientsproducts.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

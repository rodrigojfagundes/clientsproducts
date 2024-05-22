package io.github.rodrigojfagundes.clientsproducts.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.rodrigojfagundes.clientsproducts.dto.ProductDTO;
import io.github.rodrigojfagundes.clientsproducts.entities.Client;
import io.github.rodrigojfagundes.clientsproducts.entities.Product;
import io.github.rodrigojfagundes.clientsproducts.repositories.ClientRepository;
import io.github.rodrigojfagundes.clientsproducts.repositories.ProductRepository;
import io.github.rodrigojfagundes.clientsproducts.services.exceptions.DatabaseException;
import io.github.rodrigojfagundes.clientsproducts.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {
		List<Product> products = repository.findAll();
		return products.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product product = obj.orElseThrow(() -> new ResourceNotFoundException("Review not found"));
		return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product product = new Product();
		
	@SuppressWarnings("deprecation")
	Client client = clientRepository.getOne(dto.getClientId());	
	
	product.setClient(client);
	product.setProductName(dto.getProductName());
	
	product = repository.save(product);
	
	return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product product = new Product();
			
			@SuppressWarnings("deprecation")
			Client client = clientRepository.getOne(dto.getClientId());
			
			product.setClient(client);
			product.setProductName(dto.getProductName());
			
			product = repository.save(product);
			
			return new ProductDTO(product);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + "not found");
		}
	}
	
	
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("id " + id + "not found");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
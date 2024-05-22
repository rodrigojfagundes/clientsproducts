package io.github.rodrigojfagundes.clientsproducts.services;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.rodrigojfagundes.clientsproducts.dto.ClientDTO;
import io.github.rodrigojfagundes.clientsproducts.dto.ProductDTO;
import io.github.rodrigojfagundes.clientsproducts.entities.Client;
import io.github.rodrigojfagundes.clientsproducts.entities.Product;
import io.github.rodrigojfagundes.clientsproducts.repositories.ClientRepository;
import io.github.rodrigojfagundes.clientsproducts.repositories.ProductRepository;
import io.github.rodrigojfagundes.clientsproducts.services.exceptions.DatabaseException;
import io.github.rodrigojfagundes.clientsproducts.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> page = repository.find(pageRequest);
		return page.map(client -> new ClientDTO(client));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client client = obj.orElseThrow(() -> new ResourceNotFoundException("Client nao encontrado"));	
		return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client client = new Client();
		
		copyDtoToEntity(dto, client);
		client = repository.save(client);
		
		return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			@SuppressWarnings("deprecation")
			Client client = repository.getOne(id);
			copyDtoToEntity(dto, client);
			client = repository.save(client);
			return new ClientDTO(client);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + "not found");
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + "not found");
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());

		for (ProductDTO productDTO : dto.getProducts()) {
			@SuppressWarnings("deprecation")
			Product product = productRepository.getOne(productDTO.getId());
			entity.getProducts().add(product);
		}
		
	}
	
}
package io.github.rodrigojfagundes.clientsproducts.dto;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import io.github.rodrigojfagundes.clientsproducts.entities.Client;

public class ClientDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;

	private List<ProductDTO> products = new ArrayList<>();
	
	public ClientDTO() {}

	public ClientDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public ClientDTO(Client entity) {
		id = entity.getId();
		name = entity.getName();
		entity.getProducts().forEach(product -> this.products.add(new ProductDTO(product)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<ProductDTO> getProducts() {
		return products;
	}
	
}
package io.github.rodrigojfagundes.clientsproducts.dto;

import java.io.Serializable;

import io.github.rodrigojfagundes.clientsproducts.entities.Product;

public class ProductDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private String productName;
	private Long clientId;
	
	public ProductDTO() {}

	public ProductDTO(Long id, String productName, Long clientId) {
		this.id = id;
		this.productName = productName;
		this.clientId = clientId;
	}
	
	public ProductDTO(Product entity) {
		id = entity.getId();
		productName = entity.getProductName();
		clientId = entity.getClient().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
	
	
	
}

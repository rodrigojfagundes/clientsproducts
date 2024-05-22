package io.github.rodrigojfagundes.clientsproducts.dto;

import io.github.rodrigojfagundes.clientsproducts.entities.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {

}

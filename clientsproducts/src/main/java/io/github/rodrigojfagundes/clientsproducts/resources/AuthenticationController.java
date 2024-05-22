package io.github.rodrigojfagundes.clientsproducts.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.rodrigojfagundes.clientsproducts.dto.AuthenticationDTO;
import io.github.rodrigojfagundes.clientsproducts.dto.LoginResponseDTO;
import io.github.rodrigojfagundes.clientsproducts.dto.RegisterDTO;
import io.github.rodrigojfagundes.clientsproducts.entities.User;
import io.github.rodrigojfagundes.clientsproducts.entities.UserRole;
import io.github.rodrigojfagundes.clientsproducts.infra.security.TokenService;
import io.github.rodrigojfagundes.clientsproducts.repositories.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TokenService tokenService;
	
	
	@PostMapping("/login")
	public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken
				(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User) (auth.getPrincipal()));
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		if(this.repository.findByLogin(data.login()) != null) 
			return ResponseEntity.badRequest().build();
		String encryptedPassword = new BCryptPasswordEncoder().encode(
				data.password());
				
		User newUser = new User(data.login(), encryptedPassword, data.role());

		repository.save(newUser);
		return ResponseEntity.ok().build();
		
	}


}

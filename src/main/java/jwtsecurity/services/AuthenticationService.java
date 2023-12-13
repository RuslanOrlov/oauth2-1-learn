package jwtsecurity.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jwtsecurity.auth.AuthenticationRequest;
import jwtsecurity.auth.AuthenticationResponse;
import jwtsecurity.auth.RegisterRequest;
import jwtsecurity.models.User;
import jwtsecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	
	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.build();
		return null;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		return null;
	}
	
	
	
}

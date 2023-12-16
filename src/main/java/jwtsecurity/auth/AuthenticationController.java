package jwtsecurity.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jwtsecurity.services.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/auth", produces = "application/json")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;
	
	@PostMapping(path = "/register", consumes = "application/json")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest request
	) {
		// Здесь сделать проверку на наличие user-а по username (email).
		// Метод проверки реализовать в AuthenticationService, а здесь 
		// сделать возврат кода об ошибке и соответствующего сообщения. 
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping(path = "/authenticate", consumes = "application/json")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request
	) {
		return ResponseEntity.ok(service.authenticate(request));
	}
	
}

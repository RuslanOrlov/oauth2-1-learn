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
	public ResponseEntity<?> register(
			@RequestBody RegisterRequest request
	) {
		// Делаем проверку на наличие user-а по username (email) и 
		// если проверка отрицательная, возвращаем код об ошибке и 
		// соответствующее сообщение. 
		if (service.isExistsEmail(request)) {
			return ResponseEntity.badRequest().body("{\"message\":\"This email already exists\"}");
		}
		// В противном случае создаем нового пользователя. 
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping(path = "/authenticate", consumes = "application/json")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request
	) {
		return ResponseEntity.ok(service.authenticate(request));
	}
	
}

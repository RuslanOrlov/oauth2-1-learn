package jwtsecurity.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/demo")
public class DemoController {
	
	@GetMapping("/all")
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("This is a common secured API endpoint");
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	public ResponseEntity<String> userApi() {
		return ResponseEntity.ok("This is the user's secured API endpoint");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> adminApi() {
		return ResponseEntity.ok("This is the admin's secured API endpoint");
	}
	
}

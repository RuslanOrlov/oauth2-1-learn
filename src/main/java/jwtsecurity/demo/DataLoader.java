package jwtsecurity.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jwtsecurity.models.Role;
import jwtsecurity.models.User;
import jwtsecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	
	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();
		repository.save(User.builder()
				.firstname("user_firstname")
				.lastname("user_lastname")
				.email("user@mail.com")
				.password(encoder.encode("12345"))
				.role(Role.USER)
				.build()
				);
		repository.save(User.builder()
				.firstname("admin_firstname")
				.lastname("admin_lastname")
				.email("admin@mail.com")
				.password(encoder.encode("12345"))
				.role(Role.ADMIN)
				.build()
				);
	}

}

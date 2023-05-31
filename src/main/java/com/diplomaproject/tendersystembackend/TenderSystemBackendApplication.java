package com.diplomaproject.tendersystembackend;

import com.diplomaproject.tendersystembackend.model.Category;
import com.diplomaproject.tendersystembackend.model.Role;
import com.diplomaproject.tendersystembackend.model.enums.ERole;
import com.diplomaproject.tendersystembackend.repo.CategoryRepository;
import com.diplomaproject.tendersystembackend.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class TenderSystemBackendApplication {
	private final CategoryRepository categoryRepository;
	private final RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(TenderSystemBackendApplication.class, args);
	}

	@PostConstruct
	public void initialSettings(){

		List<Category> categories = new ArrayList<>();
		categories.add(new Category(1L, "IT services"));
		categories.add(new Category(2L, "Buildings"));
		categories.add(new Category(3L, "Medicine"));

		List<Role> roles = new ArrayList<>();
		roles.add(new Role(1L, ERole.ROLE_CUSTOMER));
		roles.add(new Role(2L, ERole.ROLE_EXECUTOR));
		roles.add(new Role(3L, ERole.ROLE_ADMIN));
		roles.add(new Role(4L, ERole.ROLE_MODERATOR));

		categoryRepository.saveAll(categories);
		roleRepository.saveAll(roles);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}

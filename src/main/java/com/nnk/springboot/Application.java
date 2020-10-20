package com.nnk.springboot;

import com.nnk.springboot.repositories.UserRepository;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEncryptableProperties
public class Application {

	public static void main(String[] args) {
	// ---------- Lancement des TESTS

	ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

	// Test de la connexion JPA

		UserRepository userRepository = context.getBean(UserRepository.class);
		System.out.println("Liste des users : " + userRepository.findAll() );

	// Lancement normal
	//	SpringApplication.run(Application.class, args);

	}
}

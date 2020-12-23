package com.moises.demo;

import java.util.TimeZone;
import java.util.stream.LongStream;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.moises.demo.model.Contact;
import com.moises.demo.repository.ContactRepository;

@SpringBootApplication
public class DemoApplication {
	
	@PostConstruct
    void started() {
      TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


    @Bean
    CommandLineRunner init(ContactRepository repository) {
        return args -> {
            repository.deleteAll();
            LongStream.range(1, 11)
                    .mapToObj(i -> {
                        Contact c = new Contact();
                        c.setName("Contact " + i);
                        c.setEmail("contact" + i + "@email.com");
                        c.setPhone("(111) 111-1111");
                        return c;
                    })
                    .map(v -> repository.save(v))
                    .forEach(System.out::println);
        };
    }
}
package com.example.ticket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController()
@EnableAutoConfiguration
public class TicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);
	}

	@GetMapping("/hello")

	public String hello(@RequestParam(defaultValue = "Welt") String name) {
		
		return String.format("Hello %s!!!", name);
	}

}

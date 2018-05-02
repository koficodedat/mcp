package code.kofi.mcp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.boot.SpringApplication.run;

@SuppressWarnings("WeakerAccess")
@Configuration
@SpringBootApplication
@RestController
public class App{

	public static void main(String[] args) {
		run(App.class, args);
	}

	@SuppressWarnings("SameReturnValue")
	@GetMapping("/")
	public String description(){
		return "Welcome to Multi Command Processing";
	}
	
}

package courier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootCourierMiddlewareApplication {
	
	@GetMapping("/")
	public String welcome() {
		return "Welcome to courier middleware v2.0";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCourierMiddlewareApplication.class, args);
	}

}

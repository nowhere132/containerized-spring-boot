package nowhere132;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ContainerizedSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContainerizedSpringBootApplication.class, args);
	}

}

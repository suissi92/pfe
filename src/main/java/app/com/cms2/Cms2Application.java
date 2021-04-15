package app.com.cms2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication(scanBasePackages = { "app.com" })
@ComponentScan


public class Cms2Application {

	public static void main(String[] args) {
		SpringApplication.run(Cms2Application.class, args);
	}

}

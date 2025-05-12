package hu.smthy.unique_file_lister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class UniqueFileListerApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(
				UniqueFileListerApplication.class,
				args
		);
	}
}
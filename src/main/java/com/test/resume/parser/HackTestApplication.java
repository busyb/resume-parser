package com.test.resume.parser;

import com.test.resume.parser.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class HackTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackTestApplication.class, args);
		System.out.println("Hello Java");
	}
}

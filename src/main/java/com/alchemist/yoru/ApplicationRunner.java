package com.alchemist.yoru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Atul Mundaware
 * @since 17 04 2023
 */

@EnableSwagger2
@EnableJpaAuditing
@SpringBootApplication
public class ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationRunner.class, args);
	}

}

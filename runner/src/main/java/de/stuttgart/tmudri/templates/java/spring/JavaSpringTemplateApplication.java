package de.stuttgart.tmudri.templates.java.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Application runner class. */
@SpringBootApplication
@EnableJpaRepositories
public class JavaSpringTemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(JavaSpringTemplateApplication.class, args);
  }
}

package de.stuttgart.tmudri.templates.java.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Application runner class. */
@SpringBootApplication(scanBasePackages = "de.stuttgart.tmudri.templates.java.spring.usecases")
public class JavaSpringTemplateApplicationTestRunner {

  public static void main(String[] args) {
    SpringApplication.run(JavaSpringTemplateApplicationTestRunner.class, args);
  }
}

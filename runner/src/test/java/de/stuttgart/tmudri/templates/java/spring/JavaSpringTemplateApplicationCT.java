package de.stuttgart.tmudri.templates.java.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "de.stuttgart.tmudri.templates.java")
class JavaSpringTemplateApplicationCT {

  @Test
  void contextLoads() {
    Assertions.assertTrue(true);
  }
}

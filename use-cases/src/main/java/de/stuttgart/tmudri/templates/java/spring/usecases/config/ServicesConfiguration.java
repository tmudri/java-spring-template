package de.stuttgart.tmudri.templates.java.spring.usecases.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

  @Value("${service.statistics.executor.threads}")
  private int threads;

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(threads);
  }
}

package de.stuttgart.tmudri.templates.java.spring.adapters.input.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StatusController {

  @GetMapping("/healthz")
  public ResponseEntity<ApplicationStatus> getHealth() {
    return ResponseEntity.ok(ApplicationStatus.builder().status("Service is healthy...").build());
  }

  @GetMapping("/ready")
  public ResponseEntity<String> getReady() {
    // Here we can usually check connections to other systems which application needs to run
    // correctly (e.g. connection to DB)
    return ResponseEntity.ok("Service is ready...");
  }
}

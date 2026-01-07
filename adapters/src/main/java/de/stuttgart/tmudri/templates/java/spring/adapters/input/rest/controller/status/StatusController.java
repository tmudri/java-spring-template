package de.stuttgart.tmudri.templates.java.spring.adapters.input.rest.controller.status;

import de.stuttgart.tmudri.templates.java.spring.usecases.domain.StatusStatistic;
import de.stuttgart.tmudri.templates.java.spring.usecases.ports.input.StatusProvider;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {

  private final StatusProvider statusProvider;

  public StatusController(StatusProvider statusProvider) {
    this.statusProvider = statusProvider;
  }

  @GetMapping("/healthz")
  public ResponseEntity<ApplicationStatus> getHealth() {
    var isHealthy = statusProvider.isHealthy();
    if (isHealthy) {
      return ResponseEntity.ok(ApplicationStatus.builder().status("Service is healthy...").build());
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApplicationStatus.builder().status("Service is NOT healthy...").build());
    }
  }

  @GetMapping("/ready")
  public ResponseEntity<ApplicationStatus> getReady() {
    // Here we can usually check connections to other systems which application needs to run
    // correctly (e.g. connection to DB)
    var isReady = statusProvider.isReady();
    if (isReady) {
      return ResponseEntity.ok(ApplicationStatus.builder().status("Service is ready...").build());
    } else {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
          .body(ApplicationStatus.builder().status("Service not available...").build());
    }
  }

  @GetMapping("/statistics")
  public ResponseEntity<List<StatusStatistic>> getStatusStatistics() {
    var statistics = statusProvider.getStatistics();
    return ResponseEntity.ok(statistics);
  }
}

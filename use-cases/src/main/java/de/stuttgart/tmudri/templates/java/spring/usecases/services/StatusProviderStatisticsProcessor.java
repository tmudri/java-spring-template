package de.stuttgart.tmudri.templates.java.spring.usecases.services;

import de.stuttgart.tmudri.templates.java.spring.usecases.domain.StatusStatistic;
import de.stuttgart.tmudri.templates.java.spring.usecases.ports.input.StatusProvider;
import de.stuttgart.tmudri.templates.java.spring.usecases.ports.output.StatusStatisticsStorage;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.springframework.stereotype.Component;

@Component
public class StatusProviderStatisticsProcessor implements StatusProvider {

  private final ExecutorService executorService;
  private final StatusStatisticsStorage storage;

  public StatusProviderStatisticsProcessor(
      StatusStatisticsStorage storage, ExecutorService executorService) {
    this.storage = storage;
    this.executorService = executorService;
  }

  @Override
  public boolean isHealthy() {
    // As statistics are not considered to be a key component and they should not break the core
    // domain logic they are stored in separate thread and failure is ignored. Failures are expected
    // to be uncommon.
    executorService.execute(storage::addToHealthCheckCount);
    return true;
  }

  @Override
  public boolean isReady() {
    // As statistics are not considered to be a key component and they should not break the core
    // domain logic they are stored in separate thread and failure is ignored. Failures are expected
    // to be uncommon.
    executorService.execute(storage::addToReadinessCheckCount);
    return true;
  }

  @Override
  public List<StatusStatistic> getStatistics() {
    return storage.getStatistics();
  }
}

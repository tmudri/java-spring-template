package de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db;

import static de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatisticType.HEALTH;
import static de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatisticType.READY;

import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatisticType;
import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatusProviderStatisticsEntity;
import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.repository.JpaStatusStatisticsStorageRepository;
import de.stuttgart.tmudri.templates.java.spring.core.domain.StatusStatistic;
import de.stuttgart.tmudri.templates.java.spring.core.ports.output.StatusStatisticsStorage;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HsqldbStatusStatisticsStorage implements StatusStatisticsStorage {

  private final JpaStatusStatisticsStorageRepository repository;

  public HsqldbStatusStatisticsStorage(JpaStatusStatisticsStorageRepository repository) {
    this.repository = repository;
  }

  @Override
  public void addToHealthCheckCount() {
    incrementStatusStatistics(HEALTH);
  }

  @Override
  public void addToReadinessCheckCount() {
    incrementStatusStatistics(READY);
  }

  @Override
  public List<StatusStatistic> getStatistics() {
    return repository.findAll().stream()
        .map(
            statistic ->
                StatusStatistic.builder()
                    .type(statistic.getType().toString())
                    .count(statistic.getCount())
                    .build())
        .toList();
  }

  private void incrementStatusStatistics(StatisticType type) {
    var entityOptional = repository.findByType(type);
    var entity =
        entityOptional.orElseGet(
            () -> StatusProviderStatisticsEntity.builder().type(type).count(0).build());
    entity = entity.incrementCount();
    repository.saveAndFlush(entity);
  }
}

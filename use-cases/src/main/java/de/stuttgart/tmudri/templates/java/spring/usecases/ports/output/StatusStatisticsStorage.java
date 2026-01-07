package de.stuttgart.tmudri.templates.java.spring.usecases.ports.output;

import de.stuttgart.tmudri.templates.java.spring.usecases.domain.StatusStatistic;
import java.util.List;

public interface StatusStatisticsStorage {

  void addToHealthCheckCount();

  void addToReadinessCheckCount();

  List<StatusStatistic> getStatistics();
}

package de.stuttgart.tmudri.templates.java.spring.core.ports.output;

import de.stuttgart.tmudri.templates.java.spring.core.domain.StatusStatistic;
import java.util.List;

public interface StatusStatisticsStorage {

  void addToHealthCheckCount();

  void addToReadinessCheckCount();

  List<StatusStatistic> getStatistics();
}

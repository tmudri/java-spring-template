package de.stuttgart.tmudri.templates.java.spring.usecases.ports.input;

import de.stuttgart.tmudri.templates.java.spring.usecases.domain.StatusStatistic;
import java.util.List;

public interface StatusProvider {

  boolean isHealthy();

  boolean isReady();

  List<StatusStatistic> getStatistics();
}

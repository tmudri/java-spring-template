package de.stuttgart.tmudri.templates.java.spring.core.ports.input;

import de.stuttgart.tmudri.templates.java.spring.core.domain.StatusStatistic;
import java.util.List;

public interface StatusProvider {

  boolean isHealthy();

  boolean isReady();

  List<StatusStatistic> getStatistics();
}

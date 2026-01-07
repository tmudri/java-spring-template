package de.stuttgart.tmudri.templates.java.spring.usecases.services;

import static de.stuttgart.tmudri.templates.java.spring.extensions.MockitoExtensions.awaitAtMostWithLatch;
import static de.stuttgart.tmudri.templates.java.spring.extensions.MockitoExtensions.countDownAndExecute;
import static de.stuttgart.tmudri.templates.java.spring.extensions.MockitoExtensions.countDownAndThrow;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.stuttgart.tmudri.templates.java.spring.usecases.domain.StatusStatistic;
import de.stuttgart.tmudri.templates.java.spring.usecases.exception.StorageAccessException;
import de.stuttgart.tmudri.templates.java.spring.usecases.ports.output.StatusStatisticsStorage;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatusProviderStatisticsProcessorTest {

  @Mock private StatusStatisticsStorage storage;
  private CountDownLatch countDownLatch;
  private StatusProviderStatisticsProcessor sut;

  @BeforeEach
  void init() {
    countDownLatch = new CountDownLatch(1);
    sut = new StatusProviderStatisticsProcessor(storage, Executors.newFixedThreadPool(2));
  }

  @Test
  void isHealthy_statisticsStorageOk_healthy() {
    // Given
    countDownAndExecute(countDownLatch).when(storage).addToHealthCheckCount();

    // When
    var actual = sut.isHealthy();

    // Then
    awaitAtMostWithLatch(1, TimeUnit.SECONDS, countDownLatch);

    assertTrue(actual);
    verify(storage, times(1)).addToHealthCheckCount();
  }

  @Test
  void isHealthy_statisticsStorageError_healthy() {
    // Given
    countDownAndThrow(countDownLatch, new StorageAccessException())
        .when(storage)
        .addToHealthCheckCount();

    // When
    var actual = assertDoesNotThrow(() -> sut.isHealthy());

    // Then
    awaitAtMostWithLatch(1, TimeUnit.SECONDS, countDownLatch);

    assertTrue(actual);
    verify(storage, times(1)).addToHealthCheckCount();
  }

  @Test
  void isReady_statisticsStorageOk_ready() {
    // Given
    countDownAndExecute(countDownLatch).when(storage).addToReadinessCheckCount();

    // When
    var actual = sut.isReady();

    // Then
    awaitAtMostWithLatch(1, TimeUnit.SECONDS, countDownLatch);

    assertTrue(actual);
    verify(storage, times(1)).addToReadinessCheckCount();
  }

  @Test
  void isReady_statisticsStorageError_ready() {
    // Given
    countDownAndThrow(countDownLatch, new StorageAccessException())
        .when(storage)
        .addToReadinessCheckCount();

    // When
    var actual = assertDoesNotThrow(() -> sut.isReady());

    // Then
    awaitAtMostWithLatch(1, TimeUnit.SECONDS, countDownLatch);

    assertTrue(actual);
    verify(storage, times(1)).addToReadinessCheckCount();
  }

  @Test
  void getStatistics_notExists_returnAll() {
    // When
    var actual = sut.getStatistics();

    // Then
    assertNotNull(actual);
    assertTrue(actual.isEmpty());
  }

  @Test
  void getStatistics_exists_returnAll() {
    // Given
    var statistics = List.of(StatusStatistic.builder().type("HEALTH").count(5).build());
    when(storage.getStatistics()).thenReturn(statistics);

    // When
    var actual = sut.getStatistics();

    // Then
    assertNotNull(actual);
    assertEquals(1, actual.size());
  }
}

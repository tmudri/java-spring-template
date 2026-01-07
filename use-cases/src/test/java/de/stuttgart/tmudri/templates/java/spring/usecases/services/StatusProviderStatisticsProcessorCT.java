package de.stuttgart.tmudri.templates.java.spring.usecases.services;

import static de.stuttgart.tmudri.templates.java.spring.extensions.MockitoExtensions.awaitAtMostWithLatch;
import static de.stuttgart.tmudri.templates.java.spring.extensions.MockitoExtensions.countDownAndExecute;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.stuttgart.tmudri.templates.java.spring.usecases.ports.output.StatusStatisticsStorage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StatusProviderStatisticsProcessorCT {

  @MockitoBean private StatusStatisticsStorage storage;
  private CountDownLatch countDownLatch;
  @Autowired private StatusProviderStatisticsProcessor sut;

  @BeforeEach
  void init() {
    countDownLatch = new CountDownLatch(1);
  }

  @Test
  void isHealthy_springConfigOk_healthy() {
    // Given
    countDownAndExecute(countDownLatch).when(storage).addToHealthCheckCount();

    // When
    var actual = sut.isHealthy();

    // Then
    awaitAtMostWithLatch(1, TimeUnit.SECONDS, countDownLatch);

    assertTrue(actual);
    verify(storage, times(1)).addToHealthCheckCount();
  }
}

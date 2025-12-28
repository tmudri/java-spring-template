package de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db;

import static de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatisticType.HEALTH;
import static de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatisticType.READY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatusProviderStatisticsEntity;
import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.repository.JpaStatusStatisticsStorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StatusStatisticsStorageCT {

  @Autowired private JpaStatusStatisticsStorageRepository jpaRepository;
  @Autowired private HsqldbStatusStatisticsStorage sut;

  @BeforeEach
  void init() {
    jpaRepository.deleteAllInBatch();
  }

  @Test
  void addToHealthCheckCount_notExists_countOne() {
    // When
    sut.addToHealthCheckCount();

    // Then
    var actual = jpaRepository.findByType(HEALTH);
    assertTrue(actual.isPresent());
    assertEquals(1, actual.get().getCount());
  }

  @Test
  void addToHealthCheckCount_exists_ok() {
    // Given
    var initialValue = StatusProviderStatisticsEntity.builder().type(HEALTH).count(10).build();
    jpaRepository.saveAndFlush(initialValue);

    // When
    sut.addToHealthCheckCount();

    // Then
    var actual = jpaRepository.findByType(HEALTH);
    assertTrue(actual.isPresent());
    assertEquals(11, actual.get().getCount());
  }

  @Test
  void addToReadyCheckCount_notExists_countOne() {
    // When
    sut.addToReadinessCheckCount();

    // Then
    var actual = jpaRepository.findByType(READY);
    assertTrue(actual.isPresent());
    assertEquals(1, actual.get().getCount());
  }

  @Test
  void addToReadyCheckCount_exists_ok() {
    // Given
    var initialValue = StatusProviderStatisticsEntity.builder().type(READY).count(20).build();
    jpaRepository.saveAndFlush(initialValue);

    // When
    sut.addToReadinessCheckCount();

    // Then
    var actual = jpaRepository.findByType(READY);
    assertTrue(actual.isPresent());
    assertEquals(21, actual.get().getCount());
  }

  @Test
  void getStatistics_notExist_empty() {
    // When
    var actual = sut.getStatistics();

    // Then
    assertNotNull(actual);
    assertTrue(actual.isEmpty());
  }

  @Test
  void getStatistics_exists_return() {
    // Given
    var health = StatusProviderStatisticsEntity.builder().type(HEALTH).count(33).build();
    var ready = StatusProviderStatisticsEntity.builder().type(READY).count(44).build();
    jpaRepository.saveAndFlush(health);
    jpaRepository.saveAndFlush(ready);

    // when
    var actual = sut.getStatistics();

    // Then
    assertNotNull(actual);
    assertEquals(2, actual.size());
  }
}

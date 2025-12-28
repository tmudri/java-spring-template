package de.stuttgart.tmudri.templates.java.spring.adapters.input.rest.controller.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatisticType;
import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatusProviderStatisticsEntity;
import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.repository.JpaStatusStatisticsStorageRepository;
import de.stuttgart.tmudri.templates.java.spring.core.domain.StatusStatistic;
import de.stuttgart.tmudri.templates.java.spring.core.ports.input.StatusProvider;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatusControllerCT {

  private static final String BASE_URL = "/api/v1/status";
  @Autowired private JpaStatusStatisticsStorageRepository repository;
  @Autowired private MockMvc mockMvc;
  @MockitoSpyBean private StatusProvider provider;

  @Test
  void healthz_isHealthy_ok() throws Exception {
    mockMvc.perform(get(String.format("%s/healthz", BASE_URL))).andExpect(status().isOk());
  }

  @Test
  void healthz_notHealthy_ok() throws Exception {
    Mockito.when(provider.isHealthy()).thenReturn(false);
    mockMvc
        .perform(get(String.format("%s/healthz", BASE_URL)))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void ready_isReady_ok() throws Exception {
    mockMvc.perform(get(String.format("%s/ready", BASE_URL))).andExpect(status().isOk());
  }

  @Test
  void ready_notReady_ok() throws Exception {
    Mockito.when(provider.isReady()).thenReturn(false);
    mockMvc
        .perform(get(String.format("%s/ready", BASE_URL)))
        .andExpect(status().isServiceUnavailable());
  }

  @Test
  void getStatusStatistics_healthy_ok() throws Exception {
    // Given
    // In this case I have decided to test component from Controller to DB since I use fast
    // in-memory DB.
    var objectMapper = new ObjectMapper();
    var healthStatistic =
        StatusProviderStatisticsEntity.builder().type(StatisticType.HEALTH).count(1).build();
    var readyStatistic =
        StatusProviderStatisticsEntity.builder().type(StatisticType.READY).count(2).build();
    var statisticEntities = List.of(healthStatistic, readyStatistic);
    repository.saveAllAndFlush(statisticEntities);

    // When
    var result =
        mockMvc
            .perform(get(String.format("%s/statistics", BASE_URL)))
            .andExpect(status().isOk())
            .andReturn();
    String responseJson = result.getResponse().getContentAsString();
    List<StatusStatistic> actual = objectMapper.readValue(responseJson, new TypeReference<>() {});

    // Then
    assertNotNull(actual);

    assertNotNull(actual);
    assertEquals(2, actual.size());
    var actualTypes = actual.stream().map(StatusStatistic::getType).toList();
    assertTrue(actualTypes.contains("HEALTH"));
    assertTrue(actualTypes.contains("READY"));
  }
}

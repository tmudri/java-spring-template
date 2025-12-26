package de.stuttgart.tmudri.templates.java.spring.adapters.input.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@AutoConfigureRestTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatusControllerCT {

  @Autowired private RestTestClient restTestClient;

  @Test
  void testHealthz_healthy_ok() {
    restTestClient.get().uri("/api/v1/healthz").exchange().expectStatus().isOk();
  }

  @Test
  void testReady_healthy_ok() {
    restTestClient.get().uri("/api/v1/ready").exchange().expectStatus().isOk();
  }
}

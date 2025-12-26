package de.stuttgart.tmudri.templates.java.spring.adapters.input.rest.controller;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApplicationStatus {

  String status;
}

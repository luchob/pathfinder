package com.example.patfinderd.model.validation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ApiError {

  private final HttpStatus status;
  private List<String> fieldWithErrors = new ArrayList<>();

  public ApiError(HttpStatus status) {
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void addFieldWithError(String error) {
    fieldWithErrors.add(error);
  }

  public List<String> getFieldWithErrors() {
    return fieldWithErrors;
  }

  public ApiError setFieldWithErrors(List<String> fieldWithErrors) {
    this.fieldWithErrors = fieldWithErrors;
    return this;
  }
}

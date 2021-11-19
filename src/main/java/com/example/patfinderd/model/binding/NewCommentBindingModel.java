package com.example.patfinderd.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewCommentBindingModel {

  @NotBlank
  @Size(min=10)
  private String message;

  public String getMessage() {
    return message;
  }

  public NewCommentBindingModel setMessage(String message) {
    this.message = message;
    return this;
  }
}

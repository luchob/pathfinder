package com.example.patfinderd.model.service;

public class CommentServiceModel {

  private Long routeId;
  private String message;
  private String creator;

  public Long getRouteId() {
    return routeId;
  }

  public CommentServiceModel setRouteId(Long routeId) {
    this.routeId = routeId;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public CommentServiceModel setMessage(String message) {
    this.message = message;
    return this;
  }

  public String getCreator() {
    return creator;
  }

  public CommentServiceModel setCreator(String creator) {
    this.creator = creator;
    return this;
  }
}

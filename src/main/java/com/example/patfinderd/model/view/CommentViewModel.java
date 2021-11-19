package com.example.patfinderd.model.view;

import java.time.LocalDateTime;

public class CommentViewModel {

  private Long commentId;
  private String message;
  private String user;
  private LocalDateTime created;
  private boolean canApprove;
  private boolean canDelete;

  public Long getCommentId() {
    return commentId;
  }

  public CommentViewModel setCommentId(Long commentId) {
    this.commentId = commentId;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public CommentViewModel setMessage(String message) {
    this.message = message;
    return this;
  }

  public String getUser() {
    return user;
  }

  public CommentViewModel setUser(String user) {
    this.user = user;
    return this;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public CommentViewModel setCreated(LocalDateTime created) {
    this.created = created;
    return this;
  }

  public boolean isCanApprove() {
    return canApprove;
  }

  public CommentViewModel setCanApprove(boolean canApprove) {
    this.canApprove = canApprove;
    return this;
  }

  public boolean isCanDelete() {
    return canDelete;
  }

  public CommentViewModel setCanDelete(boolean canDelete) {
    this.canDelete = canDelete;
    return this;
  }
}

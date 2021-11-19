package com.example.patfinderd.service;

import com.example.patfinderd.model.view.CommentViewModel;
import java.util.List;

public interface CommentService {

  List<CommentViewModel> getComments(Long routeId);

}

package com.example.patfinderd.service;

import com.example.patfinderd.model.service.CommentServiceModel;
import com.example.patfinderd.model.view.CommentViewModel;
import java.util.List;

public interface CommentService {

  CommentViewModel createComment(CommentServiceModel commentServiceModel);


  List<CommentViewModel> getComments(Long routeId);

}

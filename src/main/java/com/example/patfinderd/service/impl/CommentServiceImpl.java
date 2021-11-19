package com.example.patfinderd.service.impl;

import com.example.patfinderd.model.entity.Comment;
import com.example.patfinderd.model.view.CommentViewModel;
import com.example.patfinderd.repository.RouteRepository;
import com.example.patfinderd.service.CommentService;
import com.example.patfinderd.service.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  private final RouteRepository routeRepository;

  public CommentServiceImpl(RouteRepository routeRepository) {
    this.routeRepository = routeRepository;
  }

  @Transactional
  @Override
  public List<CommentViewModel> getComments(Long routeId) {
    var routeOpt = routeRepository.
        findById(routeId);

    if (routeOpt.isEmpty()) {
      throw new ObjectNotFoundException("Route with id " + routeId + " was not found!");
    }

    return routeOpt.
        get().
        getComments().
        stream().
        map(this::mapAsComment).
        collect(Collectors.toList());
  }

  private CommentViewModel mapAsComment(Comment commentEntity) {
    CommentViewModel commentViewModel = new CommentViewModel();

    commentViewModel.
        setCommentId(commentEntity.getId()).
        setCanApprove(true).
        setCanDelete(true).
        setCreated(commentEntity.getCreated()).
        setMessage(commentEntity.getTextContent()).
        setUser(commentEntity.getAuthor().getFullName());


    return commentViewModel;
  }
}

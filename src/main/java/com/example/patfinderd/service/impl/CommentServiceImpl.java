package com.example.patfinderd.service.impl;

import com.example.patfinderd.model.entity.Comment;
import com.example.patfinderd.model.service.CommentServiceModel;
import com.example.patfinderd.model.view.CommentViewModel;
import com.example.patfinderd.repository.CommentRepository;
import com.example.patfinderd.repository.RouteRepository;
import com.example.patfinderd.repository.UserRepository;
import com.example.patfinderd.service.CommentService;
import com.example.patfinderd.service.exceptions.ObjectNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  private final RouteRepository routeRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;

  public CommentServiceImpl(RouteRepository routeRepository,
      UserRepository userRepository,
      CommentRepository commentRepository) {
    this.routeRepository = routeRepository;
    this.userRepository = userRepository;
    this.commentRepository = commentRepository;
  }

  @Transactional
  @Override
  public CommentViewModel createComment(CommentServiceModel commentServiceModel) {
    var route = routeRepository.
        findById(commentServiceModel.getRouteId()).
        orElseThrow(() -> new ObjectNotFoundException("Route with id " + commentServiceModel.getRouteId() + " was not found!"));

    var user = userRepository.
        findByEmail(commentServiceModel.getCreator()).
        orElseThrow(() -> new ObjectNotFoundException("User with email " + commentServiceModel.getCreator() + " was not found!"));


    Comment comment = new Comment();
    comment.setApproved(false);
    comment.setCreated(LocalDateTime.now());
    comment.setRoute(route);
    comment.setTextContent(commentServiceModel.getMessage());
    comment.setAuthor(user);

    comment = commentRepository.save(comment);

    return mapAsComment(comment);
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

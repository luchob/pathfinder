package com.example.patfinderd.service.impl;

import com.example.patfinderd.model.entity.Route;
import com.example.patfinderd.model.service.RouteServiceModel;
import com.example.patfinderd.model.view.RouteDetailsViewModel;
import com.example.patfinderd.model.view.RouteViewModel;
import com.example.patfinderd.repository.RouteRepository;
import com.example.patfinderd.service.CategoryService;
import com.example.patfinderd.service.RouteService;
import com.example.patfinderd.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public RouteServiceImpl(RouteRepository routeRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public List<RouteViewModel> findAllRoutesView() {
        return routeRepository
                .findAll()
                .stream()
                .map(route -> {
                    RouteViewModel routeViewModel = modelMapper.map(route, RouteViewModel.class);
                    if (route.getPictures().isEmpty()) {
                        routeViewModel.setPictureUrl("/images/pic4.jpg");
                    } else {
                        routeViewModel.setPictureUrl(route.getPictures().stream().findFirst().get().getUrl());
                    }
                    return routeViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addNewRoute(RouteServiceModel routeServiceModel) {
        Route route = modelMapper.map(routeServiceModel, Route.class);
        //todo: current user
        //route.setAuthor(userService.findCurrentLoginUserEntity());

        route.setCategories(routeServiceModel
                .getCategories()
                .stream()
                .map(categoryService::findCategoryByName)
                .collect(Collectors.toList()));


        routeRepository.save(route);
    }

    @Transactional
    @Override
    public RouteDetailsViewModel findRouteById(Long id) {
        return routeRepository
                .findById(id)
                .map(route -> modelMapper.map(route, RouteDetailsViewModel.class))
                .orElse(null);
    }
}

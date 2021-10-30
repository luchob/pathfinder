package com.example.patfinderd.service;

import com.example.patfinderd.model.service.RouteServiceModel;
import com.example.patfinderd.model.view.RouteDetailsViewModel;
import com.example.patfinderd.model.view.RouteViewModel;
import java.util.List;

public interface RouteService {
    List<RouteViewModel> findAllRoutesView();

    void addNewRoute(RouteServiceModel routeServiceModel);

    RouteDetailsViewModel findRouteById(Long id);
}

package com.example.patfinderd.service;

import com.example.patfinderd.model.service.UserServiceModel;

public interface UserService {
    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findById(Long id);
}

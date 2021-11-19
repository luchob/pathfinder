package com.example.patfinderd.service.impl;

import com.example.patfinderd.model.entity.User;
import com.example.patfinderd.model.entity.enums.LevelEnum;
import com.example.patfinderd.model.service.UserServiceModel;
import com.example.patfinderd.repository.UserRepository;
import com.example.patfinderd.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        user.setLevel(LevelEnum.BEGINNER);

        userRepository.save(user);
    }


    @Override
    public UserServiceModel findById(Long id) {
        return userRepository
                .findById(id)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }
}

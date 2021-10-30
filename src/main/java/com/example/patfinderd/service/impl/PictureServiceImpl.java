package com.example.patfinderd.service.impl;

import com.example.patfinderd.repository.PictureRepository;
import com.example.patfinderd.service.PictureService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public List<String> findAllUrls() {
        return pictureRepository
                .findAllUrls();
    }
}

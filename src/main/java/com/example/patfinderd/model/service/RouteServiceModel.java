package com.example.patfinderd.model.service;

import com.example.patfinderd.model.entity.Picture;
import com.example.patfinderd.model.entity.User;
import com.example.patfinderd.model.entity.enums.CategoryNameEnum;
import com.example.patfinderd.model.entity.enums.LevelEnum;
import java.util.Set;

public class RouteServiceModel {

    private Long id;
    private String gpxCoordinates;
    private String description;
    private LevelEnum level;
    private String name;
    private User author;
    private String videoUrl;
    private Set<Picture> pictures;
    private Set<CategoryNameEnum> categories;

    public RouteServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGpxCoordinates() {
        return gpxCoordinates;
    }

    public void setGpxCoordinates(String gpxCoordinates) {
        this.gpxCoordinates = gpxCoordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public Set<CategoryNameEnum> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryNameEnum> categories) {
        this.categories = categories;
    }
}

package com.example.patfinderd.service.impl;

import com.example.patfinderd.model.entity.Category;
import com.example.patfinderd.model.entity.enums.CategoryNameEnum;
import com.example.patfinderd.repository.CategoryRepository;
import com.example.patfinderd.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryByName(CategoryNameEnum categoryNameEnum) {
        return categoryRepository
                .findByName(categoryNameEnum)
                .orElse(null);
    }
}

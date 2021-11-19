package com.example.patfinderd.service;

import com.example.patfinderd.model.entity.Category;
import com.example.patfinderd.model.entity.enums.CategoryNameEnum;

public interface CategoryService {

    Category findCategoryByName(CategoryNameEnum categoryNameEnum);
}

package com.example.patfinderd.repository;

import com.example.patfinderd.model.entity.Category;
import com.example.patfinderd.model.entity.enums.CategoryNameEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(CategoryNameEnum categoryNameEnum);
}

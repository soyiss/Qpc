package com.example.qpc.repository;

import com.example.qpc.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    CategoryEntity findByCategoryName(String categoryName);
}

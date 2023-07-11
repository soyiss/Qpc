package com.example.qpc.service;

import com.example.qpc.dto.CategoryDTO;
import com.example.qpc.entity.CategoryEntity;
import com.example.qpc.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryEntity.toSaveEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
    }

    public List<CategoryDTO> findAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryDTOList.add(CategoryDTO.toDTO(categoryEntity));
        }
        return categoryDTOList;

    }
}

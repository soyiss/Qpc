package com.example.qpc.service;

import com.example.qpc.dto.CategoryDTO;
import com.example.qpc.entity.CategoryEntity;
import com.example.qpc.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryEntity.toSaveEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
        return categoryEntity.getId();
    }


    public List<CategoryDTO> findAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryDTOList.add(CategoryDTO.toDTO(categoryEntity));
        }
        return categoryDTOList;
    }

    public CategoryDTO findById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        CategoryDTO categoryDTO = CategoryDTO.toDTO(categoryEntity);
        return categoryDTO;
    }

    public void update(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryEntity.toUpdateEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    // 다빈추가
    public Long findCategoryIdByName(String categoryName) {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(categoryName);
        if (categoryEntity != null) {
            return categoryEntity.getId();
        } else {
            // 해당 이름의 카테고리가 존재하지 않는 경우 처리
            throw new NoSuchElementException("이름이 " + categoryName + "인 카테고리를 찾을 수 없습니다.");
        }
    }
}

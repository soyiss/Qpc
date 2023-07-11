package com.example.qpc.dto;

import com.example.qpc.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String categoryName;

    public static CategoryDTO toDTO(CategoryEntity categoryEntity1) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryEntity1.getId());
        categoryDTO.setCategoryName(categoryEntity1.getCategoryName());
        return categoryDTO;
    }

}

package com.example.qpc.entity;

import com.example.qpc.dto.CategoryDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "category_table")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10,nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductEntity> productEntityList = new ArrayList<>();

    public static CategoryEntity toSaveEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryDTO.getId()); // 주석 해제하여 id 값을 설정합니다.
        categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        // 추가로 설정해야 할 필드가 있다면 여기에 추가합니다.
        return categoryEntity;
    }

    public static CategoryEntity toUpdateEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryDTO.getId());
        categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        return categoryEntity;
    }
    public static CategoryEntity toEntity(Long categoryId) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        return categoryEntity;
    }

}

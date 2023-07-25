package com.example.qpc.dto;

import com.example.qpc.entity.CategoryEntity;
import com.example.qpc.entity.ProductEntity;
import com.example.qpc.entity.ProductFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String productName;
    private int productPrice;
    private int productCount;
    private int fileAttached;
    private Long categoryId;
    private String categoryName;
    private MultipartFile productFile;
    private String originalFileName;
    private String storedFileName;

    private String uploadPath; // 파일 업로드 경로

    // 파일 경로 생성 메서드 추가
    public String getProductImagePath() {
        if (fileAttached == 1) {
            return uploadPath + storedFileName;
        } else {
            return null;
        }
    }
    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getCategoryName() {
        return categoryName;

    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public static ProductDTO toDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(productEntity.getId());
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setProductPrice(productEntity.getProductPrice());
        productDTO.setProductCount(productEntity.getProductCount());
        productDTO.setFileAttached(productEntity.getFileAttached());

        // 파일 여부에 따른 코드 추가
        if (productEntity.getFileAttached() ==1) {
            productDTO.setFileAttached(1);
            productDTO.setOriginalFileName(productEntity.getProductFileEntityList().get(0).getOriginalFileName());
            productDTO.setStoredFileName(productEntity.getProductFileEntityList().get(0).getStoredFileName());
        } else {
            productDTO.setFileAttached(0);
        }

        // 카테고리 설정
        CategoryEntity categoryEntity = productEntity.getCategoryEntity();
        if (categoryEntity != null) {
            productDTO.setCategoryName(categoryEntity.getCategoryName());
            productDTO.setCategoryId(categoryEntity.getId());
        }

        return productDTO;
    }


}

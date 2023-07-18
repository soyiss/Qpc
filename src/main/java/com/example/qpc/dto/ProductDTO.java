package com.example.qpc.dto;

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

    private Long memberId;

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
        ProductFileDTO productFileDTO = new ProductFileDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setProductCount(productEntity.getProductCount());
        productDTO.setProductPrice(productEntity.getProductPrice());

        if (productEntity.getFileAttached() == 1) {
            productDTO.setFileAttached(1);
            // 파일 이름을 담을 리스트 객체 선언
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            // 첨부파일에 각각 접근
            for (ProductFileEntity productFileEntity : productEntity.getProductFileEntityList()) {
                originalFileNameList.add(productFileEntity.getOriginalFileName());
                storedFileNameList.add(productFileEntity.getStoredFileName());
            }
            productFileDTO.setOriginalFileName(originalFileNameList);
            productFileDTO.setStoredFileName(storedFileNameList);
        } else {
            productDTO.setFileAttached(0);
        }

        return productDTO;

    }


}

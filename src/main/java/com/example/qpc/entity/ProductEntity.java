package com.example.qpc.entity;

import com.example.qpc.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "product_table")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30,nullable = false)
    private String productName;

    @Column
    private int productPrice;

    @Column
    private int productCount;

    //파일 여부를 판단하는 컬럼
    @Column
    private int fileAttached;

    @ManyToOne
    @JoinColumn(name="category_id")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductFileEntity> productFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();

    public static ProductEntity toSaveEntity(ProductDTO productDTO,CategoryEntity categoryEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductCount(productDTO.getProductCount());
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setFileAttached(0);
        return productEntity;
    }

    public static ProductEntity toUpdateEntity(ProductDTO productDTO,CategoryEntity categoryEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductCount(productDTO.getProductCount());
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setFileAttached(0);
        return productEntity;
    }

    public static ProductEntity toSaveEntityWithFile(ProductDTO productDTO,CategoryEntity categoryEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductCount(productDTO.getProductCount());
        productEntity.setFileAttached(1);
        productEntity.setCategoryEntity(categoryEntity);
        // 파일 첨부 정보 설정
        if (productDTO.getFileAttached() == 1) {
            // 파일이 첨부된 경우, 가능한 경우에만 파일 관련 속성을 설정합니다.
            if (!productDTO.getOriginalFileName().isEmpty() && !productDTO.getStoredFileName().isEmpty()) {
                List<ProductFileEntity> productFileEntities = new ArrayList<>();
                ProductFileEntity productFileEntity = new ProductFileEntity();
                productFileEntity.setOriginalFileName(productDTO.getOriginalFileName());
                productFileEntity.setStoredFileName(productDTO.getStoredFileName());
                productFileEntities.add(productFileEntity);
                productEntity.setProductFileEntityList(productFileEntities);
            }
        }

        return productEntity;
    }

    public static ProductEntity toUpdateEntityWithFile(ProductDTO productDTO,CategoryEntity categoryEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductCount(productDTO.getProductCount());
        productEntity.setFileAttached(1);
        productEntity.setCategoryEntity(categoryEntity);
        if (productDTO.getOriginalFileName() != null && !productDTO.getOriginalFileName().isEmpty()
                && productDTO.getStoredFileName() != null && !productDTO.getStoredFileName().isEmpty()) {
            List<ProductFileEntity> productFileEntities = new ArrayList<>();
            ProductFileEntity productFileEntity = new ProductFileEntity();
            productFileEntity.setOriginalFileName(productDTO.getOriginalFileName());
            productFileEntity.setStoredFileName(productDTO.getStoredFileName());
            productFileEntities.add(productFileEntity);
            productEntity.setProductFileEntityList(productFileEntities);
        }
        return productEntity;
    }

}

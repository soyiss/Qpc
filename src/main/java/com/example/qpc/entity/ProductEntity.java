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

    // 참조관계 설정(자식테이블)
    @ManyToOne // 자식이 여러개니까 (N관계) ManyToOne를 써줌
    @JoinColumn(name="member_id") //실제 DB에서 생성되는 참조 컬럼의 이름
    private MemberEntity memberEntity; //여기는 반드시 부모엔티티 타입이 온다

    @ManyToOne
    @JoinColumn(name="category_id")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductFileEntity> productFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();

    public static ProductEntity toSaveEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductCount(productDTO.getProductCount());
        productEntity.setCategoryEntity(CategoryEntity.toEntity(productDTO.getCategoryId())); // 카테고리 ID로부터 CategoryEntity 생성
        productEntity.setFileAttached(0);
        return productEntity;
    }

    public static ProductEntity toSaveEntityWithFile(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductCount(productDTO.getProductCount());
        productEntity.setCategoryEntity(CategoryEntity.toEntity(productDTO.getCategoryId()));
        productEntity.setFileAttached(1);


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

    public static ProductEntity toUpdateEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductCount(productDTO.getProductCount());
        productEntity.setCategoryEntity(CategoryEntity.toEntity(productDTO.getCategoryId())); // 카테고리 ID로부터 CategoryEntity 생성
        productEntity.setFileAttached(1);

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
}

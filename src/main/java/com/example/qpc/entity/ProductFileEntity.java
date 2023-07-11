package com.example.qpc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "product_file_table")
public class ProductFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=100)
    private String originalFileName;
    @Column(length=100)
    private String storedFileName;

    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity productEntity;

    public static ProductFileEntity toSaveProductFileEntity(ProductEntity savedEntity, String originalFileName, String storedFileName) {
        ProductFileEntity productFileEntity = new ProductFileEntity();
        productFileEntity.setProductEntity(savedEntity);
        productFileEntity.setOriginalFileName(originalFileName);
        productFileEntity.setStoredFileName(storedFileName);
        return productFileEntity;


    }
}

package com.example.qpc.repository;


import com.example.qpc.entity.ProductFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFileRepository extends JpaRepository<ProductFileEntity, Long> {
    ProductFileEntity findByProductEntityId(Long id);

    void deleteByProductEntityId(Long id);
}

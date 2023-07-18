package com.example.qpc.service;

import com.example.qpc.dto.ProductDTO;
import com.example.qpc.entity.CategoryEntity;
import com.example.qpc.entity.ProductEntity;
import com.example.qpc.entity.ProductFileEntity;
import com.example.qpc.repository.CategoryRepository;
import com.example.qpc.repository.ProductFileRepository;
import com.example.qpc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductFileRepository productFileRepository;
    private final CategoryRepository categoryRepository;

    public Long save(ProductDTO productDTO) throws IOException {
        if (productDTO.getProductFile() == null || productDTO.getProductFile().isEmpty()) {
            // 파일 없음
            ProductEntity productEntity = ProductEntity.toSaveEntity(productDTO);
            return productRepository.save(productEntity).getId();
        } else {
            // 파일 있음
            // 1. Product 테이블에 데이터 먼저 저장
            ProductEntity productEntity = ProductEntity.toSaveEntityWithFile(productDTO);

            // 2. 카테고리 저장 및 설정
            Long categoryId = productDTO.getCategoryId();
            if (categoryId != null) {
                CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
                productEntity.setCategoryEntity(categoryEntity);
            }

            ProductEntity savedEntity = productRepository.save(productEntity);

            // 3. 업로드된 파일 정보를 받아서 각각 ProductFileEntity로 변환 후 product_file_table에 저장
            MultipartFile file = productDTO.getProductFile();
            String originalFileName = file.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
            String savePath = "D:\\springboot_img\\" + storedFileName;
            file.transferTo(new File(savePath));

            // 4. ProductFileEntity로 변환 후 저장
            ProductFileEntity productFileEntity = new ProductFileEntity();
            productFileEntity.setOriginalFileName(originalFileName);
            productFileEntity.setStoredFileName(storedFileName);
            productFileEntity.setProductEntity(savedEntity);
            productFileRepository.save(productFileEntity);

            return savedEntity.getId();
        }
    }


    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (ProductEntity product : products) {
            ProductDTO productDTO = new ProductDTO();
            // 기존 코드 유지

            // 카테고리 이름 설정
            productDTO.setCategoryName(product.getCategoryEntity().getCategoryName());

            productDTOs.add(productDTO);
        }

        return productDTOs;
    }

    @Transactional
    public List<ProductDTO> findAll() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productEntityList.forEach(productEntity -> {
            productDTOList.add(ProductDTO.toDTO(productEntity));
        });
        return productDTOList;

    }

    @Transactional
    public ProductDTO findByProductId(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        String uploadPath = "D:\\springboot_img\\"; // 실제 업로드 경로로 수정해야 합니다.

        ProductDTO productDTO = ProductDTO.toDTO(productEntity);

        // 카테고리 정보가 있는 경우 카테고리 이름을 설정합니다
        CategoryEntity categoryEntity = productEntity.getCategoryEntity();
        if (categoryEntity != null) {
            productDTO.setCategoryName(categoryEntity.getCategoryName());
        }

        return productDTO;
    }
}

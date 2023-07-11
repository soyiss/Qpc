package com.example.qpc.service;

import com.example.qpc.dto.ProductDTO;
import com.example.qpc.entity.ProductEntity;
import com.example.qpc.entity.ProductFileEntity;
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

    public Long save(ProductDTO productDTO) throws IOException {
        if (productDTO.getProductFile() == null || productDTO.getProductFile().isEmpty()) {
            // 파일 없음
            ProductEntity productEntity = ProductEntity.toSaveEntity(productDTO);
            return productRepository.save(productEntity).getId();
        } else {
            // 파일 있음
            // 1. Product 테이블에 데이터 먼저 저장
            ProductEntity productEntity = ProductEntity.toSaveEntity(productDTO);
            ProductEntity savedEntity = productRepository.save(productEntity);

            // 2. 업로드된 파일 정보를 받아서 각각 ProductFileEntity로 변환 후 product_file_table에 저장
            MultipartFile file = productDTO.getProductFile();
            String originalFileName = file.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
            String savePath = "D:\\springboot_img\\" + storedFileName;
            file.transferTo(new File(savePath));

            // 3. ProductFileEntity로 변환 후 저장
            ProductFileEntity productFileEntity =
                    ProductFileEntity.toSaveProductFileEntity(savedEntity, originalFileName, storedFileName);
            productFileRepository.save(productFileEntity);

            return savedEntity.getId();
        }
    }



}

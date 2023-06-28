package com.example.qpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    private Long memberId;

    private MultipartFile productFile;

}

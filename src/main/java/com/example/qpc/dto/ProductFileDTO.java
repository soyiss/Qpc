package com.example.qpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFileDTO {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private Long productId;
}

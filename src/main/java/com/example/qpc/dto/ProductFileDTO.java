package com.example.qpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFileDTO {
    private Long id;
    private List<String> originalFileName;
    private List<String> storedFileName;
    private Long productId;
}

package com.example.qpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private Long memberId;
    private Long productId;
    private int totalPrice;
    private int totalCount;
    private String memo;
    private String createAt;
}

package com.example.qpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Long seatId;
    private Long productId;
    private Long memberId;
    private int productPrice;
    private int productCount;
    private int totalPrice;

}

package com.example.qpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeDTO {
    private Long id;
    private Long memberId;
    private String paymentDate; //결제일시
    private int amount; //결제
    private int time; //충전시간(분단위)
    private String paymentMethod; //결제수단






}

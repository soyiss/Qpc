package com.example.qpc.dto;

import lombok.Data;

@Data
public class PaymentInformationDTO {
    int amount;
    int time;
    Long memberId;
}

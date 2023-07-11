package com.example.qpc.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PaymentMemberDTO {

    @NotEmpty(message = "필수사항입니다")
    public String keyword;
}

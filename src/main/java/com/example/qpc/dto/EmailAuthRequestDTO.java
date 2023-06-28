package com.example.qpc.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmailAuthRequestDTO {

    @NotEmpty(message = "이메일을 입력해주세요")
    public String email;
}

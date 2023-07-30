package com.example.qpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutCheckDTO {
    private Long id;
    private int overTime;
    private boolean isAdmin;
}

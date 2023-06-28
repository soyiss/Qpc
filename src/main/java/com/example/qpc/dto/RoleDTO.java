package com.example.qpc.dto;

import com.example.qpc.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private String name;


    public enum RoleEntity {
        ROLE_MEMBER, ROLE_ADMIN, ROLE_GUEST
    }

}

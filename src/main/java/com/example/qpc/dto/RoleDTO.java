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


    public RoleDTO toDTO(RoleEntity roleEntity) {
        RoleDTO roleDTO = new RoleDTO(roleEntity.name());
        return roleDTO;
    }

}

package com.example.qpc.dto;

import com.example.qpc.entity.CategoryEntity;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.entity.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

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


    public static TimeDTO toDTO(TimeEntity timeEntity) {
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setId(timeEntity.getId());
        timeEntity.getMemberEntity().getId(); // memberId를 설정해줌
        timeDTO.setAmount(timeEntity.getAmount());
        timeDTO.setTime(timeEntity.getTime());
        timeDTO.setPaymentMethod(timeEntity.getPaymentMethod());
        return timeDTO;
    }

    public static TimeDTO toUpdateDTO(TimeEntity timeEntity) {
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setId(timeEntity.getId());
        timeDTO.setMemberId(timeEntity.getMemberEntity().getId());
        timeDTO.setAmount(timeEntity.getAmount());
        timeDTO.setTime(timeEntity.getTime());
        timeDTO.setPaymentMethod(timeEntity.getPaymentMethod());
        return timeDTO;
    }



}

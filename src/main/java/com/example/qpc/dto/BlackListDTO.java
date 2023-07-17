package com.example.qpc.dto;

import com.example.qpc.entity.BlackListEntity;
import com.example.qpc.util.UtilClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlackListDTO {
    private Long id;
    private Long memberId; //회원의 id값을 참조(회원번호)

    private String memberLoginId; //회원이 로그인할떄 쓰는 아이디
    private String memberBirth;
    private String memberName;
    private String createAt;

    //BlackListEntity를 받아서 BlackListDTO 객체로 변환
    public static BlackListDTO toDTO(BlackListEntity blackListEntity) {
        BlackListDTO blackListDTO = new BlackListDTO();
        blackListDTO.setId(blackListEntity.getId());
        blackListDTO.setMemberLoginId(blackListEntity.getMemberLoginId());
        blackListDTO.setMemberBirth(blackListEntity.getMemberBirth());
        blackListDTO.setMemberName(blackListEntity.getMemberName());
        blackListDTO.setCreateAt(UtilClass.dateFormat(blackListEntity.getCreatedAt()));

        return blackListDTO;
    }
}

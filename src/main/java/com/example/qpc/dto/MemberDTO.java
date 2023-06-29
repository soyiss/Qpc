package com.example.qpc.dto;

import com.example.qpc.entity.MemberEntity;
import com.example.qpc.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long id; //회원번호
    private String memberId; //아이디
    private String memberPassword; //비밀번호
    private String memberName; //이름
    private String memberEmail; //이메일
    private String memberMobile; //전화번호
    private String memberBirth; //생년월일
    private int overTime; //잔여시간
    private int totalTime; //총시간
    private String createAt; //생성시간
    private String updateAt; //수정시간

    private RoleEntity role; // 관리자 권한주는 필드


}

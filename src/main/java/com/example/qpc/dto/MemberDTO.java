package com.example.qpc.dto;

import com.example.qpc.entity.MemberEntity;
import com.example.qpc.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.Role;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private Long id; //회원번호

    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String memberId; //아이디

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String memberPassword; //비밀번호

    @NotBlank(message = "이름은 필수 입력입니다.")
    private String memberName; //이름

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String memberEmail; //이메일

    @NotBlank(message = "전화번호는 필수 입력입니다.")
    private String memberMobile; //전화번호

    @NotBlank(message = "생년월일은 필수 입력입니다.")
    private String memberBirth; //생년월일

    private int overTime; //잔여시간
    private int totalTime; //총시간
    private String createAt; //생성시간
    private String updateAt; //수정시간

    private RoleEntity role; // 관리자 권한주는 필드



    public MemberDTO(String memberId, String memberPassword, String memberName,
                     String memberEmail, String memberBirth, String memberMobile,
                     RoleEntity role) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberBirth = memberBirth;
        this.memberMobile = memberMobile;
        this.role = role;
    }

    public static MemberDTO createMember(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
        MemberDTO member = MemberDTO.builder()
                .memberId(memberDTO.getMemberId())
                .memberPassword(passwordEncoder.encode(memberDTO.getMemberPassword())) // 패스워드 암호화
                .memberName(memberDTO.getMemberName())
                .memberEmail(memberDTO.getMemberEmail())
                .memberBirth(memberDTO.getMemberBirth())
                .memberMobile(memberDTO.getMemberMobile())
                .role(memberDTO.getRole())
                .build();
        return member;
    }




}

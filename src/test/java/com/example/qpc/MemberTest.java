package com.example.qpc;

import com.example.qpc.dto.MemberDTO;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
//@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public class MemberTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberDTO createMemberDTO() {
        MemberDTO memberDTO = MemberDTO.builder()
                .memberId("testId")
                .memberPassword("testPassword")
                .memberName("testName")
                .memberEmail("lsw3674@gmail.com")
                .memberMobile("01092554626")
                .memberBirth("1997-03-13")
                .build();
        System.out.println("memberDTO = " + memberDTO);
        return MemberDTO.createMember(memberDTO, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        MemberDTO memberDTO = createMemberDTO();
        MemberEntity memberEntity = memberService.saveMember(memberDTO);
        System.out.println("memberDTO = " + memberDTO);
        System.out.println("memberEntity = " + memberEntity);

        assertEquals(memberDTO.getMemberEmail(), memberEntity.getMemberEmail());
    }

    @Test
    @Rollback
    @DisplayName("아이디 찾기 테스트")
    public void findByIdTest() {
        MemberDTO member = createMemberDTO();
        memberService.saveMember(member);
        MemberDTO memberDTO = memberService.findByMemberEmail("lsw3674@gmail.com");
        System.out.println("memberDTO = " + memberDTO);
        if (memberDTO != null) {
            System.out.println("성공 : " + memberDTO);
        } else {
            System.out.println("테스트 실패");
        }
    }

    @Test
    @Rollback
    @DisplayName("비밀번호 찾기 테스트")
    public void findByEmailAndId() {
        MemberDTO member = createMemberDTO();
        memberService.saveMember(member);
        MemberDTO memberDTO = memberService.findByMemberEmailAndMemberId("lsw3674@gmail.com", "testId");
        System.out.println("memberDTO = " + memberDTO);
        if (memberDTO != null) {
            System.out.println("성공 : " + memberDTO);
        } else {
            System.out.println("테스트 실패");
        }
    }
}

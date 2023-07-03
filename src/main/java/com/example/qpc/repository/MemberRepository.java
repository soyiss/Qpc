package com.example.qpc.repository;

import com.example.qpc.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    
    // 중복가입 회원이 있는지 memberEmail 중복체크 메소드
    MemberEntity findByMemberEmail(String memberEmail);

    MemberEntity findByMemberIdAndMemberPassword(String memberId, String memberPassword);

    MemberEntity findByMemberId(String memberId);

}

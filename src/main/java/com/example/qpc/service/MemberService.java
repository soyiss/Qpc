package com.example.qpc.service;

import com.example.qpc.dto.MemberDTO;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // @Transactional = 로직을 처리하다 에러가 발생하면 변경된 데이터를 로직을 수행하기 이전 상태로 롤백


    public MemberEntity saveMember(MemberDTO memberDTO) {
        validateDuplicateMemberEntity(memberDTO);
        return memberRepository.save(MemberEntity.toEntity(memberDTO));
    }

    private void validateDuplicateMemberEntity(MemberDTO memberDTO) {
        MemberEntity findMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(findMemberEntity != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}

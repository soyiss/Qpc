package com.example.qpc.service;

import com.example.qpc.dto.MemberDTO;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;

    // @Transactional = 로직을 처리하다 에러가 발생하면 변경된 데이터를 로직을 수행하기 이전 상태로 롤백


    // 회원가입 코드
    public MemberEntity saveMember(MemberDTO memberDTO) {
        // 회원가입시 이메일 , 아이디 중복 체크
        validateDuplicateMemberEmail(memberDTO);
        validateDuplicateMemberId(memberDTO);
        return memberRepository.save(MemberEntity.toEntity(memberDTO));
    }


    // 회원가입시 이메일 중복 체크
    private void validateDuplicateMemberEmail(MemberDTO memberDTO) {
        MemberEntity findMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(findMemberEntity != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    // 회원가입시 아이디 중복 체크
    private void validateDuplicateMemberId(MemberDTO memberDTO) {
        MemberEntity findMemberEntity = memberRepository.findByMemberId(memberDTO.getMemberId());
        if(findMemberEntity != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(email);

        if(memberEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(memberEntity.getMemberId())
                .password(memberEntity.getMemberPassword())
                .roles(memberEntity.getRole().toString())
                .build();
    }


    public MemberDTO findByMemberId(String memberId) {
        MemberEntity memberEntity = memberRepository.findByMemberId(memberId);
        if(memberEntity == null) {
            return null;
        }
        return MemberDTO.toDTO(memberEntity);
    }

    public MemberDTO findByMemberEmail(String memberEmail) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(memberEntity == null) {
            return null;
        }else {
            return MemberDTO.toDTO(memberEntity);
        }
    }

    public MemberDTO findByMemberEmailAndMemberId(String memberEmail, String memberId) {
        MemberEntity memberEntity = memberRepository.findByMemberEmailAndMemberId(memberEmail,memberId);
        if(memberEntity == null) {
            return null;
        }else {
            return MemberDTO.toDTO(memberEntity);
        }
    }

    public MemberDTO findById(Long id) {
        return MemberDTO.toDTO(memberRepository.findById(id).get());
    }

    public MemberDTO memberUpdate(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberRepository.save(MemberEntity.toUpdateEntity(memberDTO));
        return MemberDTO.toDTO(memberEntity);
    }
}

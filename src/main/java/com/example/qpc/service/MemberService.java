package com.example.qpc.service;

import com.example.qpc.config.DuplicateMemberException;
import com.example.qpc.dto.MemberDTO;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.entity.RoleEntity;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;

    // @Transactional = 로직을 처리하다 에러가 발생하면 변경된 데이터를 로직을 수행하기 이전 상태로 롤백


    // 회원가입 코드
    public MemberEntity saveMember(MemberDTO memberDTO) {
        // 회원가입시 중복체크
        validateDuplicateMemberEmail(memberDTO);
        validateDuplicateMemberId(memberDTO);
        return memberRepository.save(MemberEntity.toEntity(memberDTO));
    }


    // 회원가입시 이메일 중복 체크
    private void validateDuplicateMemberEmail(MemberDTO memberDTO) {
        MemberEntity findMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (findMemberEntity != null) {
            throw new DuplicateMemberException("이미 가입된 이메일입니다.");
        }
    }

    // 회원가입시 아이디 중복 체크
    private void validateDuplicateMemberId(MemberDTO memberDTO) {
        MemberEntity findMemberEntity = memberRepository.findByMemberId(memberDTO.getMemberId());
        if (findMemberEntity != null) {
            throw new DuplicateMemberException("이미 가입된 아이디입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByMemberId(username);

        if (memberEntity == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // 사용자 정보를 UserDetails 구현체로 반환
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
        System.out.println("memberEntity.getTotalTime() = " + memberEntity.getTotalTime());
        System.out.println("memberEntity.getOverTime() = " + memberEntity.getOverTime());
        return MemberDTO.toDTO(memberEntity);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    //관리자때 필요한 회원리스트 메서드
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;

    }

    public MemberDTO findByRole() {
        MemberEntity memberEntity = memberRepository.findByRole("ADMIN");
        if(memberEntity != null) {
            return MemberDTO.toDTO(memberEntity);
        }else {
            return null;
        }
    }

    public void save(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateEntity(memberDTO));
    }


}

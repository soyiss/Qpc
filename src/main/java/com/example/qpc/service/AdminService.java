package com.example.qpc.service;

import com.example.qpc.dto.BlackListDTO;
import com.example.qpc.dto.MemberDTO;
import com.example.qpc.entity.BlackListEntity;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.repository.AdminRepository;
import com.example.qpc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;



    public boolean addBlack(MemberDTO memberDTO) {

        System.out.println("뭐있니 memberDTO = " + memberDTO);
            MemberEntity memberEntity = memberRepository.findByMemberId(memberDTO.getMemberId());
            BlackListEntity blackListEntity = BlackListEntity.toSaveBlackListEntity(memberEntity, memberEntity.getMemberBirth(), memberEntity.getMemberName(), memberEntity.getMemberId());
        System.out.println("냠blackListEntity = " + blackListEntity.getMemberLoginId());
        BlackListEntity existingBlackListEntity = adminRepository.findByMemberLoginId(blackListEntity.getMemberLoginId());

            if (existingBlackListEntity == null) {
            adminRepository.save(blackListEntity);
            return true;
        }else{
            System.out.println("이미 블랙리스트에 존재하는 회원입니다.");
            return false;
        }

    }



    public List<BlackListDTO> findAll() {
        List<BlackListEntity> blackListEntityList = adminRepository.findAll();
        List<BlackListDTO> blackListDTOList = new ArrayList<>();
        blackListEntityList.forEach(blackListEntity -> {
            blackListDTOList.add(BlackListDTO.toDTO(blackListEntity));
        });
        return blackListDTOList;
    }



    public boolean delete(Long id) {
        adminRepository.deleteById(id);
        return true;
    }


    public boolean isBlackListed(MemberDTO memberDTO) {
        List<BlackListEntity> blackListEntityList = adminRepository.findAll();
        return blackListEntityList.stream()
                .anyMatch(blackListEntity -> blackListEntity.getMemberLoginId().equals(memberDTO.getMemberId()));
    }
}

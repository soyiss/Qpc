package com.example.qpc.service;

import com.example.qpc.dto.MemberDTO;
import com.example.qpc.dto.TimeDTO;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.entity.TimeEntity;
import com.example.qpc.repository.MemberRepository;
import com.example.qpc.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO, TimeDTO timeDTO) {
        MemberEntity member = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        timeRepository.save(TimeEntity.toEntity(timeDTO,member));
    }
}

package com.example.qpc.service;

import com.example.qpc.dto.MemberDTO;
import com.example.qpc.entity.MemberEntity;
import com.example.qpc.repository.MemberRepository;
import com.example.qpc.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    public MemberDTO paymentFindById(String keyword) {
        MemberEntity memberEntity = memberRepository.findByMemberId(keyword);
       if(memberEntity == null) {
           throw new EntityNotFoundException(keyword+"에 해당하는 회원이 없습니다");
       }
       return MemberDTO.toDTO(memberEntity);
    }
}

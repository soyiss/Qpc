package com.example.qpc.controller;

import com.example.qpc.dto.EmailAuthRequestDTO;
import com.example.qpc.dto.MemberDTO;
import com.example.qpc.dto.PaymentInformationDTO;
import com.example.qpc.dto.PaymentMemberDTO;
import com.example.qpc.service.MemberService;
import com.example.qpc.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final MemberService memberService;

    @PostMapping("/findById")
    public ResponseEntity paymentFindById(@RequestBody PaymentMemberDTO paymentMemberDTO) throws MessagingException, UnsupportedEncodingException {
        System.out.println("paymentMemberDTO = " + paymentMemberDTO);
        try {
            MemberDTO memberDTO = paymentService.paymentFindById(paymentMemberDTO.getKeyword());
            return new ResponseEntity<>(memberDTO.getId(),HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/memberCharge")
    public ResponseEntity memberCharge(@RequestBody PaymentInformationDTO paymentInformationDTO) {
        MemberDTO memberDTO = memberService.findById(paymentInformationDTO.getMemberId());
        System.out.println("memberDTO = " + memberDTO);
        memberDTO.setOverTime(memberDTO.getOverTime()+paymentInformationDTO.getTime());
        memberDTO.setTotalTime(memberDTO.getTotalTime()+ paymentInformationDTO.getTime());
        memberService.memberUpdate(memberDTO);

        return new ResponseEntity<>(memberDTO,HttpStatus.OK);
    }

}

package com.example.qpc.controller;

import com.example.qpc.dto.*;
import com.example.qpc.handler.CustomLogoutHandler;
import com.example.qpc.service.MemberService;
import com.example.qpc.service.PaymentService;
import com.example.qpc.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final MemberService memberService;
    private final TimeService timeService;

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
    public ResponseEntity memberCharge(@RequestBody PaymentInformationDTO paymentInformationDTO, HttpSession session ) {
        System.out.println("paymentInformationDTO = " + paymentInformationDTO);
        MemberDTO memberDTO = memberService.findById(paymentInformationDTO.getMemberId());
        System.out.println("memberDTO = " + memberDTO);
        memberDTO.setOverTime(memberDTO.getOverTime()+paymentInformationDTO.getTime());
        memberDTO.setTotalTime(memberDTO.getTotalTime()+ paymentInformationDTO.getTime());
        memberService.memberUpdate(memberDTO);
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setAmount(paymentInformationDTO.getAmount());
        timeDTO.setTime(paymentInformationDTO.getTime());
        timeDTO.setPaymentMethod("KakaoPay");
        timeService.save(memberDTO,timeDTO);
        session.invalidate();
        return new ResponseEntity<>(memberDTO,HttpStatus.OK);
    }

}

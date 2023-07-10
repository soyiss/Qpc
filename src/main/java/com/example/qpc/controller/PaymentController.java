package com.example.qpc.controller;

import com.example.qpc.dto.EmailAuthRequestDTO;
import com.example.qpc.dto.MemberDTO;
import com.example.qpc.dto.PaymentMemberDTO;
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
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment/findById")
    public ResponseEntity paymentFindById(@RequestBody PaymentMemberDTO paymentMemberDTO) throws MessagingException, UnsupportedEncodingException {
        System.out.println("paymentMemberDTO = " + paymentMemberDTO);
        try {
            MemberDTO memberDTO = paymentService.paymentFindById(paymentMemberDTO.getKeyword());
            return new ResponseEntity<>(memberDTO.getId(),HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

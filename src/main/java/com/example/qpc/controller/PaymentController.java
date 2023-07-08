package com.example.qpc.controller;

import com.example.qpc.dto.EmailAuthRequestDTO;
import com.example.qpc.dto.PaymentMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    @PostMapping("/payment/findById")
    public ResponseEntity paymentFindById(@RequestBody PaymentMemberDTO paymentMemberDTO) throws MessagingException, UnsupportedEncodingException {
        System.out.println("paymentMemberDTO = " + paymentMemberDTO);
        return new ResponseEntity<>(paymentMemberDTO, HttpStatus.OK);
    }

}

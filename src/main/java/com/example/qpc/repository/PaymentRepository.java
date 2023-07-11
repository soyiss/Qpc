package com.example.qpc.repository;

import com.example.qpc.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<MemberEntity,Long> {

}

package com.example.qpc.repository;

import com.example.qpc.entity.BlackListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<BlackListEntity, Long> {

    BlackListEntity findByMemberLoginId(String memberLoginId);
}

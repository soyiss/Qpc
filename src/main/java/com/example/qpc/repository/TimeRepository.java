package com.example.qpc.repository;

import com.example.qpc.entity.MemberEntity;
import com.example.qpc.entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface TimeRepository extends JpaRepository<TimeEntity,Long> {


    List<TimeEntity> findByMemberEntity(MemberEntity memberEntity);
}

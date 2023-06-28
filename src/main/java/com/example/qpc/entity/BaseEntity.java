package com.example.qpc.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {

    @CreationTimestamp //insert전용
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp //업데이트 전용
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}

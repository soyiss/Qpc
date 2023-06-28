package com.example.qpc.entity;

import com.example.qpc.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "member_table")
@Builder
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30,nullable = false,unique = true)
    private String memberId;

    @Column(length = 30,nullable = false)
    private String memberPassword;

    @Column(length = 20,nullable = false)
    private String memberName;

    @Column(length = 50,nullable = false)
    private String memberEmail;

    @Column(length = 20,nullable = false)
    private String memberMobile;

    @Column(length = 10,nullable = false)
    private String memberBirth;

    @Column
    private int overTime;
    @Column
    private int totalTime;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductEntity> productEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SeatEntity> seatEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlackListEntity> blackListEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TimeEntity> timeEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatEntity> chatEntityList = new ArrayList<>();

    public static MemberEntity toEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberId(memberDTO.getMemberId())
                .memberPassword(memberDTO.getMemberPassword())
                .memberName(memberDTO.getMemberName())
                .memberEmail(memberDTO.getMemberEmail())
                .memberMobile(memberDTO.getMemberMobile())
                .memberBirth(memberDTO.getMemberBirth())
                .overTime(memberDTO.getOverTime())
                .totalTime(memberDTO.getTotalTime())
                .role(memberDTO.getRole())
                .build();
        return memberEntity;
    }


}

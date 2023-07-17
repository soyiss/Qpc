package com.example.qpc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "blackList_table")
public class BlackListEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30,nullable = false,unique = true)
    private String memberLoginId;

    @Column(length = 10,nullable = false)
    private String memberBirth;

    @Column(length = 20,nullable = false)
    private String memberName;
    // 참조관계 설정(자식테이블)
    @ManyToOne // 자식이 여러개니까 (N관계) ManyToOne를 써줌
    @JoinColumn(name="member_id") //실제 DB에서 생성되는 참조 컬럼의 이름
    private MemberEntity memberEntity; //여기는 반드시 부모엔티티 타입이 온다

    // MemberEntity, 저장해야 할 정보들을 받아 BlackListEntity 객체로 변환합니다.
    public static BlackListEntity toSaveBlackListEntity(MemberEntity memberEntity, String memberBirth, String memberName,String memberLoginId) {
        BlackListEntity blackListEntity = new BlackListEntity();
        blackListEntity.setMemberEntity(memberEntity);
        blackListEntity.setMemberLoginId(memberLoginId);
        blackListEntity.setMemberBirth(memberBirth);
        blackListEntity.setMemberName(memberName);
        return blackListEntity;
    }

}

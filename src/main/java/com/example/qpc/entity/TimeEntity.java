package com.example.qpc.entity;

import com.example.qpc.dto.TimeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "time_table")
public class TimeEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int amount;
    @Column
    private int time;
    @Column(length = 50, nullable = false)
    private String paymentMethod;

    // 참조관계 설정(자식테이블)
    @ManyToOne // 자식이 여러개니까 (N관계) ManyToOne를 써줌
    @JoinColumn(name="member_id") //실제 DB에서 생성되는 참조 컬럼의 이름
    private MemberEntity memberEntity; //여기는 반드시 부모엔티티 타입이 온다

    @OneToMany(mappedBy = "timeEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SeatEntity> seatEntityList = new ArrayList<>();

    public static TimeEntity toEntity(TimeDTO timeDTO,MemberEntity memberEntity) {
        TimeEntity timeEntity = new TimeEntity();
        timeEntity.setAmount(timeDTO.getAmount());
        timeEntity.setTime(timeDTO.getTime());
        timeEntity.setPaymentMethod(timeDTO.getPaymentMethod());
        timeEntity.setMemberEntity(memberEntity);
        return timeEntity;
    }

    public static TimeEntity toUpdateEntity(TimeDTO timeDTO,MemberEntity memberEntity) {
        TimeEntity timeEntity = new TimeEntity();
        timeEntity.setId(timeDTO.getId());
        timeEntity.setAmount(timeDTO.getAmount());
        timeEntity.setTime(timeDTO.getTime());
        timeEntity.setPaymentMethod(timeDTO.getPaymentMethod());
        timeEntity.setMemberEntity(memberEntity);
        return timeEntity;
    }
}

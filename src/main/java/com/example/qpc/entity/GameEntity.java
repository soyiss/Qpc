package com.example.qpc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "game_table")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10,nullable = false)
    private String gameName;

    @Column(length = 500,nullable = false)
    private String gameLink;

    //파일 여부를 판단하는 컬럼
    @Column
    private int fileAttached;

    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameFileEntity> gameFileEntityList = new ArrayList<>();
}

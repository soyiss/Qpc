package com.example.qpc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "game_file_table")
public class GameFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=100)
    private String originalFileName;
    @Column(length=100)
    private String storedFileName;

    @ManyToOne
    @JoinColumn(name="game_id")
    private GameEntity gameEntity;
}

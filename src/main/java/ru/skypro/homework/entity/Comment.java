package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @JoinColumn()
    @ManyToOne
    Ad ad;

    @JoinColumn()
    @ManyToOne
    User user;

    long createdAt;

    String text;
}
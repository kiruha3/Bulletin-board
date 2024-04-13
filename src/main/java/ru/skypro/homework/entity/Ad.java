package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;//author
    private Integer price;
    private String title;
    @OneToOne
    private Image image;//
    private String description;
    private LocalDateTime dateTime;
}

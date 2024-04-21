package ru.skypro.homework.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User author;
    private BigDecimal price;
    private String title;
    @OneToOne
    private Image image;
    private String description;
    private LocalDateTime dateTime;
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<Comment> comments;
}

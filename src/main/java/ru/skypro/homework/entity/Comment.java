package ru.skypro.homework.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Integer count;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @OneToOne
    private Image image;// authorImage;
    private String authorFirstName;
    private Long createdAt;  // TODO нужно LocalDateTime
    private String text;
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
}

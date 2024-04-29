package ru.skypro.homework.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    private String authorFirstName;
    private Long createdAt;
    private String text;
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt / 1000 * 1000;
    }
}

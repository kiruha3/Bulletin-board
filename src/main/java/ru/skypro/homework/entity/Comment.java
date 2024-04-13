package ru.skypro.homework.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.skypro.homework.dto.comments.CommentDto;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer count;

    @ManyToOne
    private User user;

    @OneToOne
    private Image image;// authorImage;

    private String authorFirstName;

    private Long createdAt;

    private String text;
}

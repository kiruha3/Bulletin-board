package ru.skypro.homework.entity;

import java.util.List;
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
}

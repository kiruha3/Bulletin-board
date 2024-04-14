package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;

@Deprecated
@Service
public class CommentMapper {

    public Comment toEntity (CreateOrUpdateCommentDto createOrUpdateCommentDto){
        Comment comment = new Comment();
        comment.setText(createOrUpdateCommentDto.getText());
        return comment;
    }

    public CommentDto toDto(Comment comment){

        CommentDto commentDto = new CommentDto();
        commentDto.setPk(Math.toIntExact(comment.getId()));
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setText(comment.getText());
        commentDto.setAuthor(Math.toIntExact(comment.getUser().getId()));
        commentDto.setAuthorFirstName(comment.getUser().getFirstName());
        commentDto.setAuthorImage(String.valueOf(comment.getImage()));

        return commentDto;
    }

}

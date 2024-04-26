package ru.skypro.homework.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comments.CommentDto;

import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {UserNotFoundException.class})
public abstract class CommentMapper {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageService imageService;

    @Mapping(source = "text", target = "text")
    public abstract Comment createOrUpdateCommmentDtoToComment(CreateOrUpdateCommentDto createOrUpdateCommentDto);

    public CommentDto commentToCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setPk(comment.getId());
        commentDto.setAuthorFirstName(comment.getAuthorFirstName());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setText(comment.getText());

        commentDto.setAuthor(comment.getAuthor().getId());
        commentDto.setAuthorImage(imageService.getUserImageUrl(comment.getAuthor().getId()));

        return commentDto;
    }

    public CommentsDto commentsToCommentsDto(List<Comment> commentList) {
        CommentsDto commentsDto = new CommentsDto();

        commentsDto.setCount(commentList.size());

        ArrayList<CommentDto> commentDtoResult = new ArrayList<>();
        for (Comment comment : commentList) {
            commentDtoResult.add(commentToCommentDto(comment));
        }

        commentsDto.setResults(commentDtoResult);

        return commentsDto;
    }
}

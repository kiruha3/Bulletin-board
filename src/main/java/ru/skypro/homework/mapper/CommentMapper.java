package ru.skypro.homework.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comments.CommentDto;

import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;

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

    @Mapping(source = "id", target = "pk")
    @Mapping(target = "author", expression = "java(comment.getAuthor().getId())")
    @Mapping(target = "authorImage", expression = "java(comment.getAuthorImage().getFilePath())")
    public abstract CommentDto commentToCommentDto(Comment comment);

    @Mapping(source = "pk", target = "id")
    @Mapping(target = "author", expression = "java(userRepository.findById(commentDto.getAuthor()).orElseThrow(UserNotFoundException::new))")
    @Mapping(target = "authorImage", expression = "java(imageService.getImage(comment.getId()))")
    public abstract Comment commentDtoToComment(CommentDto commentDto);

    @Mapping(source = "text", target = "text")
    public abstract Comment createOrUpdateCommmentDtoToComment(CreateOrUpdateCommentDto createOrUpdateCommentDto);

    public abstract List<Comment> commentToCommentDto(List<Comment> commentList);

}

package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.service.UserService;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final AdService adService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public CommentsDto getAllCommentsForAd(Integer adId) {
        return commentMapper.commentsToCommentsDto(commentRepository.findAllByAdId(adId));
    }

    @Override
    public CommentDto createNewComment(Integer adsId, CreateOrUpdateCommentDto commentDto, Authentication authentication) {
        log.info("Был вызван метод создания нового комментария", CommentService.class.getSimpleName());
        User user = userService.getUser(authentication.getName());
        Ad adsById = adService.getAdById(adsId);
        Comment comment = commentMapper.createOrUpdateCommmentDtoToComment(commentDto);
        comment.setAuthor(user);
        comment.setAuthorFirstName(user.getFirstName());
        comment.setCreatedAt(Instant.now().toEpochMilli());
        comment.setAd(adsById);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(savedComment);
    }

    @Override
    public CommentDto getComments(Integer commentId) {
        Comment comment = commentRepository.findCommentById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        return commentMapper.commentToCommentDto(comment);
    }

    @Override
    public void deleteComments( Integer commentId) {
        Comment comment = commentRepository.findCommentById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComments(Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        Comment comment = commentRepository.findCommentById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        Comment infoToUpdate = commentMapper.createOrUpdateCommmentDtoToComment(createOrUpdateCommentDto);

        comment.setText(infoToUpdate.getText());
        comment.setCreatedAt(Instant.now().toEpochMilli());

        return commentMapper.commentToCommentDto(commentRepository.save(comment));
    }
}
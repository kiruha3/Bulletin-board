package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;

public interface CommentService {
    CommentsDto getAllCommentsForAd(Integer adId);

    CommentDto createNewComment(Integer adId, CreateOrUpdateCommentDto commentDto, Authentication authentication);

    CommentDto getComments( Integer commentId);

    void deleteComments( Integer commentId);

    CommentDto updateComments(Integer commentId, CommentDto commentDto);
}

package ru.skypro.homework.service;

import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;

public interface CommentService {
    CommentsDto getAllCommentsForAdsWithId(Long adsId);

    CommentDto createNewComment(Integer adId, CommentDto commentDto);

    CommentDto getComments(Integer adPk, Integer id);

    void deleteComments(Integer adPk, Integer id);

    CommentDto updateComments(Integer adPk, Integer id, CommentDto commentDto);
}

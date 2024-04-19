package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.exception.CommentNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final AdService adService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @Override
    public CommentsDto getAllCommentsForAdsWithId(Long adsId) {
        return null;
    }

    @Override
    public CommentDto createNewComment(Integer adsId, CommentDto commentDto) {
        log.info("Был вызван метод создания нового комментария", CommentService.class.getSimpleName());
        Ad adsById = adService.getAdsById(adsId);
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        comment.setAd(adsById);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(savedComment);
    }

    @Override
    public CommentDto getComments(Integer adPk, Integer id) {
        Comment comment = commentRepository.findCommentByIdAndAuthorId(adPk, id)
                .orElseThrow(CommentNotFoundException::new);
        return commentMapper.commentToCommentDto(comment);
    }

    @Override
    public void deleteComments(Integer adPk, Integer id) {
        Comment comment = commentRepository.findCommentByIdAndAuthorId(adPk, id)
                .orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);

    }

    @Override
    public CommentDto updateComments(Integer adPk, Integer id, CommentDto commentDto) {
        Comment comment = commentRepository.findCommentByIdAndAuthorId(adPk, id)
                .orElseThrow(CommentNotFoundException::new);
        comment.setText(comment.getText());
        commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }

}

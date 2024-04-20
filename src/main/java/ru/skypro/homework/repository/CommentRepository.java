package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query("SELECT s from Ad s")
    List<Comment> getAll();
    Optional<Comment> findCommentByIdAndAuthorId (Integer commentId, Integer authorId);
}

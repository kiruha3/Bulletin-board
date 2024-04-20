package ru.skypro.homework.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

@Component
public class UserSecurity {
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean isAdsAuthor(Integer adsId) {
        try {
            User adAuthor = adRepository.findById(adsId).orElseThrow(AdNotFoundException::new).getAuthor();
            User authentificatedUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                                                        .orElseThrow(UserNotFoundException::new);

            return adAuthor.equals(authentificatedUser);
        } catch (AdNotFoundException | UserNotFoundException e) {
            return false;
        }
    }

    public boolean isCommentAuthor(Integer commentId) {
        try {
            User commentAuthor = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new).getAuthor();
            User authentificatedUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(UserNotFoundException::new);

            return commentAuthor.equals(authentificatedUser);
        } catch (AdNotFoundException | UserNotFoundException e) {
            return false;
        }
    }
}

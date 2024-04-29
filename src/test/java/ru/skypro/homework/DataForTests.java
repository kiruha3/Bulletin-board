package ru.skypro.homework;

import org.springframework.http.MediaType;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DataForTests extends AdDto {
    public static Ad getDefaultAd() {
        Ad result = new Ad();

        result.setId(1);
        result.setTitle("Ad for tests title");
        result.setDescription("Ad for test desc");
        result.setPrice(BigDecimal.valueOf(128));
        result.setAuthor(getDefaultUser());
        result.setImage(getDefaltImage());
        result.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return result;
    }

    public static User getDefaultUser() {
        User user = new User();

        user.setId(1);
        user.setEmail("osuser@gmail.com");
        user.setFirstName("Os");
        user.setLastName("User");
        user.setPhone("+78005553535");
        user.setUsername(user.getEmail());
        user.setEnabled(true);

        return user;
    }

    public static Image getDefaltImage() {
        Image image = new Image();

        image.setId(1L);
        image.setMediaType(MediaType.IMAGE_JPEG_VALUE);
        image.setFilePath("Ad");

        return image;
    }

    public static Image getSecondDefaltImage() {
        Image image = new Image();

        image.setId(2L);
        image.setMediaType(MediaType.IMAGE_JPEG_VALUE);
        image.setFilePath("Knuckles");

        return image;
    }

    public static byte[] getDefaultImageData() {
        return "Здесь могла быть ваша реклама".getBytes();
    }

    public static byte[] getSecondDefaultImageData() {
        return "Do you know da wae?".getBytes();
    }

    public static List<Ad> getDefaultAdList() {
        List<Ad> result = new ArrayList<>();

        result.add(getDefaultAd());

        Ad ad2 = getDefaultAd();
        ad2.setId(2);
        result.add(ad2);

        return result;
    }

    public static Comment getDefaultComment() {
        Comment comment = new Comment();

        comment.setId(1);
        comment.setCreatedAt(Instant.now().toEpochMilli());
        comment.setAd(getDefaultAd());
        comment.setText("Qq");
        comment.setAuthor(getDefaultUser());
        comment.setAuthorFirstName(getDefaultUser().getFirstName());

        return comment;
    }

    public static List<Comment> getDefaultCommentList() {
        List<Comment> result = new ArrayList<>();

        result.add(getDefaultComment());

        Comment comment2 = getDefaultComment();
        comment2.setId(2);
        comment2.setText("Ку");
        result.add(comment2);

        return result;
    }
}

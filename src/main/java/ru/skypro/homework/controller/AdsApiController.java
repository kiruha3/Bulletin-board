package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import javax.annotation.processing.Generated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-04-06T11:46:42.537169258Z[GMT]")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsApiController {
    private static final Logger log = LoggerFactory.getLogger(AdsApiController.class);

    private final ObjectMapper objectMapper;
    private final AdService adService;
    private final CommentService commentService;
    private final HttpServletRequest request;

    @Autowired
    public AdsApiController(ObjectMapper objectMapper, AdService adService, CommentService commentService, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.adService = adService;
        this.commentService = commentService;
        this.request = request;
    }

    // Этот метод позволяет создавать новые объявления, принимая данные о свойствах и изображении, и возвращая результат в соответствии с ожидаемым типом ответа.
    @PostMapping(value = "/ads") // указывает, что данный метод будет обрабатывать POST запросы по пути "/ads"
    public ResponseEntity<AdDto> addAd(@RequestPart(value = "properties", required = false) CreateOrUpdateAdDto properties, // Параметр properties представляет данные для создания или обновления объявления в формате CreateOrUpdateAdDto. Помечен опциональным (required = false).
                                       @Valid @RequestPart(value = "image", required = false) MultipartFile image,
                                       Authentication authentication) { // Параметр image (изображение) связанное с объявлением. Аннотация @Valid - необходимость проведения валидации параметра. Параметр опциональный.
        return new ResponseEntity<>(adService.creatAd(properties, image, authentication), HttpStatus.CREATED); // ResponseEntity<AdDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/ads/{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable("id") Integer id) {
        log.info("Was invoked get full ad by id = {} method", id);
        return ResponseEntity.ok(adService.getExtendedAdDto(id));
    }

    @GetMapping(value = "/ads/me")
    public ResponseEntity<AdsDto> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adService.getAdsDtoMe(authentication.getName()));
    }

    @GetMapping(value = "/ads")
    public ResponseEntity<AdsDto> getAds() {
        log.info("\n" + "Был вызван метод получения всех объявлений");
        return ResponseEntity.ok(adService.getAllAds());
    }

    @DeleteMapping(value = "/ads/{id}")
    @PreAuthorize("@userSecurity.isAdsAuthor(#id) or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id) {
        log.info("\n" + "Был вызван метод удаления объявления с помощью id = {}", id);
        adService.deletedAd(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/ads/{id}")
    @PreAuthorize("@userSecurity.isAdsAuthor(#id) or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AdDto> updateAds(@PathVariable("id") Integer id, @Valid @RequestBody CreateOrUpdateAdDto body) {
        log.info("\n" + "Был вызван метод обновления объявления по id = {}", id);
        return ResponseEntity.ok(adService.updateAd(id, body));
    }

    @GetMapping(value = "/ads/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) {
        log.info("Был вызван метод на получение изображения");
        return ResponseEntity.ok(adService.getAdImage(id));
    }

    @PatchMapping(value = "/ads/{id}/image")
    public ResponseEntity<byte[]> updateImage(@PathVariable("id") Integer id, @Valid @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(adService.updateImage(id, image));
    }

    @GetMapping("/ads/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(commentService.getAllCommentsForAd(id));
    }

    @PostMapping("/ads/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable("id") Integer id, @Valid @RequestBody CreateOrUpdateCommentDto body, Authentication authentication) {
        log.info("Был вызван запрос на добавление комментария для объявления = {} ", id);
        return ResponseEntity.ok(commentService.createNewComment(id, body, authentication));
    }

    @PreAuthorize("@userSecurity.isCommentAuthor(#id) or hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("adId") Integer adId, @PathVariable("commentId") Integer commentId, @Valid @RequestBody CommentDto body) { //TODO разобраться с подменой тела CreateOrUpdateCommentDto body)
        log.info("Было вызвано  обновление объявление = {} в комментарии id = {}", adId, commentId);
        return ResponseEntity.ok(commentService.updateComments(adId, commentId, body));
    }

    @PreAuthorize("@userSecurity.isCommentAuthor(#id) or hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("adId") Integer adId, @PathVariable("commentId") Integer commentId) {
        log.info("Was invoked delete ad's comment by id = {} method", commentId);
        commentService.deleteComments(adId, commentId);
        return ResponseEntity.ok().build();
    }
}

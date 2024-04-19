package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import javax.annotation.processing.Generated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-04-06T11:46:42.537169258Z[GMT]")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsApiController {

    private static final Logger log = LoggerFactory.getLogger(AdsApiController.class);

    private final ObjectMapper objectMapper;
    private final AdService adService;
    private final HttpServletRequest request;

    @Autowired
    public AdsApiController(ObjectMapper objectMapper, AdService adService, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.adService = adService;
        this.request = request;
    }

    // Этот метод позволяет создавать новые объявления, принимая данные о свойствах и изображении, и возвращая результат в соответствии с ожидаемым типом ответа.
    @PostMapping(value = "/ads") // указывает, что данный метод будет обрабатывать POST запросы по пути "/ads"
    public ResponseEntity<AdDto> addAd(@RequestPart(value = "properties", required = false) CreateOrUpdateAdDto properties, // Параметр properties представляет данные для создания или обновления объявления в формате CreateOrUpdateAdDto. Помечен опциональным (required = false).
                                       @Valid @RequestPart(value = "image", required = false) MultipartFile image) { // Параметр image (изображение) связанное с объявлением. Аннотация @Valid - необходимость проведения валидации параметра. Параметр опциональный.
        return new ResponseEntity<>(adService.creatAd(properties, image), HttpStatus.CREATED); // ResponseEntity<AdDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/ads/{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable("id") Integer id) {
        log.info("Was invoked get full ad by id = {} method", id);
        return ResponseEntity.ok(adService.getExtendedAdDto(id));
    }
    @GetMapping(value = "/ads/me")
    public ResponseEntity<AdsDto> getAdsMe() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AdsDto>(objectMapper.readValue("{\n  \"count\" : 0,\n  \"results\" : [ {\n    \"image\" : \"image\",\n    \"author\" : 6,\n    \"price\" : 5,\n    \"pk\" : 1,\n    \"title\" : \"title\"\n  }, {\n    \"image\" : \"image\",\n    \"author\" : 6,\n    \"price\" : 5,\n    \"pk\" : 1,\n    \"title\" : \"title\"\n  } ]\n}", AdsDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<AdsDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AdsDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/ads")
    public ResponseEntity<AdsDto> getAds() {
        log.info("\n" + "Был вызван метод получения всех объявлений");
        return ResponseEntity.ok(adService.getAllAds());
    }

    @DeleteMapping(value = "/ads/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id) {
        log.info("\n" + "Был вызван метод удаления объявления с помощью id = {}", id);
        adService.deletedAd(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/ads/{id}")
    public ResponseEntity<AdDto> updateAds(@PathVariable("id") Integer id, @Valid @RequestBody CreateOrUpdateAdDto body) {
        log.info("\n" + "Был вызван метод обновления объявления по id = {}", id);
        return ResponseEntity.ok(adService.updateAd(id, body));
    }
    @PatchMapping(value = "/ads/{id}/image")
    public ResponseEntity<List<byte[]>> updateImage(@PathVariable("id") Integer id, @Valid @RequestPart(value = "image", required = false) MultipartFile image) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<byte[]>>(objectMapper.readValue("[ \"\", \"\" ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<byte[]>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<byte[]>>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<CommentsDto>(objectMapper.readValue("{\n  \"count\" : 0,\n  \"results\" : [ {\n    \"createdAt\" : 1,\n    \"authorFirstName\" : \"authorFirstName\",\n    \"author\" : 6,\n    \"authorImage\" : \"authorImage\",\n    \"pk\" : 5,\n    \"text\" : \"text\"\n  }, {\n    \"createdAt\" : 1,\n    \"authorFirstName\" : \"authorFirstName\",\n    \"author\" : 6,\n    \"authorImage\" : \"authorImage\",\n    \"pk\" : 5,\n    \"text\" : \"text\"\n  } ]\n}", CommentsDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<CommentsDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<CommentsDto>(HttpStatus.NOT_IMPLEMENTED);
    }
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable("id") Integer id, @Valid @RequestBody CreateOrUpdateCommentDto body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<CommentDto>(objectMapper.readValue("{\n  \"createdAt\" : 1,\n  \"authorFirstName\" : \"authorFirstName\",\n  \"author\" : 6,\n  \"authorImage\" : \"authorImage\",\n  \"pk\" : 5,\n  \"text\" : \"text\"\n}", CommentDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<CommentDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<CommentDto>(HttpStatus.NOT_IMPLEMENTED);
    }
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("adId") Integer adId, @PathVariable("commentId") Integer commentId, @Valid @RequestBody CreateOrUpdateCommentDto body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<CommentDto>(objectMapper.readValue("{\n  \"createdAt\" : 1,\n  \"authorFirstName\" : \"authorFirstName\",\n  \"author\" : 6,\n  \"authorImage\" : \"authorImage\",\n  \"pk\" : 5,\n  \"text\" : \"text\"\n}", CommentDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<CommentDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<CommentDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("adId") Integer adId, @PathVariable("commentId") Integer commentId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
}

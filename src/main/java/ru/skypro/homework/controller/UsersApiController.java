package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.service.UserService;

import javax.annotation.processing.Generated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-04-06T11:46:42.537169258Z[GMT]")
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping
public class UsersApiController {
    @Autowired
    private UserService userService;

    //TODO: Удалить
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    @Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @GetMapping(value = "/users/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserDto(authentication.getName()));
    }

    //TODO: Реализовать
    @PostMapping(value = "/users/set_password")
    public ResponseEntity<Void> setPassword(@Valid @RequestBody NewPasswordDto body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    //TODO: Реализовать
    @PatchMapping(value = "/users/me")
    public ResponseEntity<UpdateUserDto> UpdateUserDto(@Valid @RequestBody UpdateUserDto body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UpdateUserDto>(objectMapper.readValue("{\n  \"firstName\" : \"firstName\",\n  \"lastName\" : \"lastName\",\n  \"phone\" : \"phone\"\n}", UpdateUserDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UpdateUserDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<UpdateUserDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    //TODO: Реализовать
    @PatchMapping(value = "/users/me/image")
    public ResponseEntity<Void> updateUserImage(@Valid @RequestPart(value = "image", required = false) MultipartFile image) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/users/{id}/image")
    public ResponseEntity<byte[]> getUserImage(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserImage(id));
    }
}

package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@Deprecated
public class UsersController {


    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto dto) {
        return ResponseEntity.ok(dto);
    }


    public ResponseEntity<UserDto> getUser() {
        UserDto dto = new UserDto();
        return ResponseEntity.ok(dto);
    }


    public ResponseEntity<UpdateUserDto> updateUser(@RequestBody UpdateUserDto dto) {
        return ResponseEntity.ok(dto);
    }


    public ResponseEntity<Void> updateUserImage(MultipartFile image) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

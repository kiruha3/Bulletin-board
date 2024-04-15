package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginDto;
import ru.skypro.homework.service.AuthService;

import javax.annotation.processing.Generated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-04-06T11:46:42.537169258Z[GMT]")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class LoginApiController {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final AuthService authService;

    @Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request, AuthService authService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDto loginDto) {
        if (authService.login(loginDto.getUsername(), loginDto.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

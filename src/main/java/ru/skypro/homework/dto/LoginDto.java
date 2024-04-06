package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class LoginDto {
    /**
     * Login
     */

    @JsonProperty("password")
    @Size(min=8,max=16)
    private String username;

    @JsonProperty("username")
    private String password;
}

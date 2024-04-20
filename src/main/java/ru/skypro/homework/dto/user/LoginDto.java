package ru.skypro.homework.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class LoginDto {
    /**
     * Login
     */

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    @Size(min=8,max=16)
    private String password;
}

package ru.skypro.homework.dto.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CreateOrUpdateCommentDto {
    @JsonProperty("text")
    @Size (min = 8, max = 64)
    private String text;
}

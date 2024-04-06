package ru.skypro.homework.dto.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentDto {
    /**
     * Comment
     */
    @JsonProperty("author")
    private Integer author;

    @JsonProperty("authorImage")
    private String authorImage;

    @JsonProperty("authorFirstName")
    private String authorFirstName;

    @JsonProperty("createdAt")
    private Long createdAt;

    @JsonProperty("pk")
    private Integer pk;

    @JsonProperty("text")
    private String text;

}
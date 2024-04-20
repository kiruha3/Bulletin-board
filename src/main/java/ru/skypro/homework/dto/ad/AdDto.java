package ru.skypro.homework.dto.ad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdDto {
    @JsonProperty("author")
    private Integer author;

    @JsonProperty("image")
    private String image;

    @JsonProperty("pk")
    private Integer pk;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("title")
    private String title;

}

package ru.skypro.homework.dto.ad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class AdsDto {
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    @Valid
    private List<AdDto> results;
}

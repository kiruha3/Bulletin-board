package ru.skypro.homework.dto.ad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class AdsDto {
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    @Valid
    private List<AdDto> results;

    public AdsDto results(List<AdDto> results) {
        this.results = results;
        return this;
    }

    public AdsDto addResultsItem(AdDto resultsItem) {
        if (this.results == null) {
            this.results = new ArrayList<AdDto>();
        }
        this.results.add(resultsItem);
        return this;
    }

}

package ru.skypro.homework.dto.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@Data
public class CommentsDto {

    /**
     * Comments
     */
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    @Valid
    private List<CommentDto> results;

}

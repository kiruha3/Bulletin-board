package ru.skypro.homework.dto.AD;

import lombok.Data;

import java.util.List;

@Data
public class AdsDto {

    private Integer count;
    private List<AdDto> result;


}

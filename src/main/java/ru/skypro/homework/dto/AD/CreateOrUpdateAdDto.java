package ru.skypro.homework.dto.AD;

import lombok.Data;


@Data
public class CreateOrUpdateAdDto {
    private String description;
    private String title;
    private Integer price;
}

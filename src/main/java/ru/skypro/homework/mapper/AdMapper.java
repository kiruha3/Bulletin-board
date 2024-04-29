package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring",
    imports = LocalDateTime.class)
public abstract class AdMapper {
    @Autowired
    ImageServiceImpl imageService;
    @Mapping(target = "dateTime", expression = "java(LocalDateTime.now())")
    public abstract Ad createAdsDtoToAds(CreateOrUpdateAdDto createOrUpdateAdDto);

    @Mapping(target = "author", expression = "java(ad.getAuthor().getId())")
    @Mapping(target = "image", expression = "java(imageService.getAdImageUrl(ad.getId()))")
    @Mapping(target = "pk", source = "id")
    public abstract AdDto adToAdDto(Ad ad);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "image", expression = "java(imageService.getAdImageUrl(ad.getId()))")
    @Mapping(target = "email", expression = "java(ad.getAuthor().getUsername())")
    @Mapping(target = "authorFirstName", expression = "java(ad.getAuthor().getFirstName())")
    @Mapping(target = "authorLastName", expression = "java(ad.getAuthor().getLastName())")
    @Mapping(target = "phone", expression = "java(ad.getAuthor().getPhone())")
    public abstract ExtendedAdDto adToExtendedAdDto(Ad ad);

    public AdsDto getAds(List<Ad> ads) {
        AdsDto adsDto = new AdsDto();

        adsDto.setCount(ads.size());

        ArrayList<AdDto> adDtoResult = new ArrayList<>();
        for (Ad ad : ads) {
            adDtoResult.add(adToAdDto(ad));
        }
        adsDto.setResults(adDtoResult);

        return adsDto;
    }
}
package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Named;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdMapper {


    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    //@Mapping(target = "price", source = "price")
    //@Mapping(target = "title", source = "title")
    @Mapping(target = "image", source = "images", qualifiedByName = "getListOfImageLinks")
    public  AdDto adsToAdsDto(Ad ad);

    public  Ad createAdsDtoToAds(CreateOrUpdateAdDto createOrUpdateAdDto);
    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(source = "author.lastName", target = "authorLastName")
    @Mapping(source = "author.username", target = "email")
    @Mapping(source = "author.phone", target = "phone")
    @Mapping(source = "id", target = "pk")
    @Mapping(source = "images", target = "image", qualifiedByName = "getListOfImageLinks")
    public  ExtendedAdDto adsToFullAdsDto(Ad ads);

    @Mapping(target = "author", source = "user.id")
    List<AdDto> adToAdsDtoList(List<Ad> adList);

    public List<AdDto> mapListOfAdsToListDTO(List<Ad> listAds);
    @Named("getListOfImageLinks")
    default List<String> getListOfImageLinks(List<Image> images) {
        return (images == null || images.isEmpty()) ? null :
                images.stream().map(i -> "/image/" + i.getId()).collect(Collectors.toList());
    }
}
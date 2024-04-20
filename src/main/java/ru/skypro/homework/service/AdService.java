package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;

public interface AdService {
    AdsDto getAllAds();

    AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, Authentication authentication);

    ExtendedAdDto getExtendedAdDto(Integer id);

    AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);

    AdsDto getAdsDtoMe(String username);

    void deletedAd(Integer idPk);

    byte[] getAdImage(int id);

    Ad getAdById(Integer id);

    byte[] updateImage(int id, MultipartFile image);

}

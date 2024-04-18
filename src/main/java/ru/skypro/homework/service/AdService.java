package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;

public interface AdService {
    AdsDto getAllAds();

    AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, Authentication authentication);

    ExtendedAdDto getExtendedAdDto(int id);

    AdDto updateAd(int id, CreateOrUpdateAdDto createOrUpdateAdDto, Authentication authentication);

    AdsDto getAdDtoMe(String userName);

    void deletedAd(int idPk, String userName);

    void uploadImage(int id, MultipartFile image, Authentication authentication);

    byte[] getAdImage(String filename);
}

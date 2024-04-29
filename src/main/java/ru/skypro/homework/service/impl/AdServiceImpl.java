package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.util.ArrayList;


@Slf4j
@RequiredArgsConstructor
@Service
public  class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final AdMapper adMapper;

    /**
     * Метод выводит AdsDto (кол-во объявлений и все объявления)
     *
     * @return AdsDto
     */
    @Override
    public AdsDto getAllAds() {
        return adMapper.getAds(adRepository.findAll());
    }

    @Override
    @Transactional // Аннотация @Transactional обеспечивает управление транзакциями в методе. Это означает, что все операции в методе будут выполнены в рамках одной транзакции базы данных. В случае успешного выполнения всех операций транзакция будет закрыта и изменения зафиксированы в базе данных. Если произойдет ошибка, транзакция будет откатана.
    public AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, Authentication authentication) {
        log.info("Был вызван метод создания объявлений из {}", AdService.class.getSimpleName());
        User user = userService.getUser(authentication.getName());
        Ad ads = adMapper.createAdsDtoToAds(createOrUpdateAdDto);

        ads.setAuthor(user);

        Ad savedAds = adRepository.save(ads);

        Image adsImage = imageService.addImage(image);

        savedAds.setImage(adsImage);

        return adMapper.adToAdDto(savedAds);
    }

    @Override
    public ExtendedAdDto getExtendedAdDto(Integer idPk) {
        return adMapper.adToExtendedAdDto(getAdById(idPk));
    }

    @Override
    public AdDto updateAd(Integer idPk, CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad oldAds = getAdById(idPk);
        Ad infoToUpdate = adMapper.createAdsDtoToAds(createOrUpdateAdDto);

        oldAds.setPrice(infoToUpdate.getPrice());
        oldAds.setTitle(infoToUpdate.getTitle());
        oldAds.setDescription(infoToUpdate.getDescription());

        adRepository.save(oldAds);

        return adMapper.adToAdDto(oldAds);
    }

    @Override
    public AdsDto getAdsDtoMe(String userName) {
        return adMapper.getAds(adRepository.findByAuthorId(userService.getUser(userName).getId()).orElse(new ArrayList<>()));
    }

    @Override
    public void deletedAd(Integer idPk) {
        adRepository.delete(getAdById(idPk));
    }

    @Override
    public byte[] updateImage(int id, MultipartFile image) {
        Ad ad = getAdById(id);
        ad.setImage(imageService.addImage(image));

        adRepository.save(ad);

        return getAdImage(id);
    }

    @Override
    public byte[] getAdImage(int id) {
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);

        if (ad.getImage() == null) {
            throw new ImageNotFoundException();
        }

        return imageService.getImageData(ad.getImage().getId());
    }

    @Override
    public Ad getAdById(Integer id) {
        return adRepository.findById(id).orElseThrow(AdNotFoundException::new);
    }
}

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
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.util.List;


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
        List<Ad> adList = adRepository.findAll();
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adList.size());
        adsDto.setResults(adMapper.mapListOfAdsToListDTO(adList));
        return adsDto;
    }

    @Override
    @Transactional // Аннотация @Transactional обеспечивает управление транзакциями в методе. Это означает, что все операции в методе будут выполнены в рамках одной транзакции базы данных. В случае успешного выполнения всех операций транзакция будет закрыта и изменения зафиксированы в базе данных. Если произойдет ошибка, транзакция будет откатана.
    public AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, Authentication authentication) {

        log.info("Был вызван метод создания объявлений из {}", AdService.class.getSimpleName());
        //User user = userService.getUser(authentication.getName());
        Ad ads = adMapper.createAdsDtoToAds(createOrUpdateAdDto);

        //ads.setAuthor(user);

        Ad savedAds = adRepository.save(ads);

        Image adsImage = imageService.createImage(image, savedAds);
        savedAds.setImage(adsImage);
        return adMapper.adsToAdsDto(savedAds);
    }

    @Override
    public ExtendedAdDto getExtendedAdDto(Integer idPk) {
        Ad ads = getAdsById(idPk);
        return adMapper.adsToFullAdsDto(ads);
    }

    @Override
    public AdDto updateAd(Integer idPk, CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad oldAds = getAdsById(idPk);
        Ad infoToUpdate = adMapper.createAdsDtoToAds(createOrUpdateAdDto);

        oldAds.setPrice(infoToUpdate.getPrice());
        oldAds.setTitle(infoToUpdate.getTitle());
        oldAds.setDescription(infoToUpdate.getDescription());

        Ad updatedAds = adRepository.save(oldAds);
        return adMapper.adsToAdsDto(updatedAds);
    }

    @Override
    public AdsDto getAdDtoMe(String userName) {
        return null;
    }

    @Override
    public void deletedAd(Integer idPk) {
        Ad ads = getAdsById(idPk);
        adRepository.delete(ads);
    }

    @Override
    public void uploadImage(int id, MultipartFile image) {

    }


    @Override
    public byte[] getAdImage(String filename) {
        return new byte[0];
    }

    @Override
    public Ad getAdsById(Integer id) {
        return adRepository.findById(id).orElseThrow(AdNotFoundException::new);
    }

}

package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdMapperTest {

    @Spy
    private AdMapper adMapper = new AdMapperImpl();
    @Test
    public void testCreateAdsDtoToAds() {
        // Создать объект createOrUpdateAdDto
        CreateOrUpdateAdDto createOrUpdateAdDto = new CreateOrUpdateAdDto();
        createOrUpdateAdDto.setTitle("Test Ad");

        // Вызвать метод createAdsDtoToAds и проверить корректность возвращаемого значения

        Ad ad = adMapper.createAdsDtoToAds(createOrUpdateAdDto);

        // Проверить, что объект Ad создан успешно и содержит верные значения
        assertNotNull(ad);
        assertEquals("Test Ad", ad.getTitle());
    }

    @Test
    public void testAdToAdDto() {
        // Создать объект Ad
        Ad ad = new Ad();
        ad.setId(1);

        // Вызвать метод adToAdDto и проверить корректность возвращаемого значения
        AdDto adDto = adMapper.adToAdDto(ad);

        // Проверить, что объект AdDto создан успешно
        assertNotNull(adDto);
        // Добавить здесь необходимые проверки значений
    }

    @Test
    public void testAdToExtendedAdDto() {
        // Создать объект Ad
        Ad ad = new Ad();
        ad.setId(1);

        // Вызвать метод adToExtendedAdDto и проверить корректность возвращаемого значения
        ExtendedAdDto extendedAdDto = adMapper.adToExtendedAdDto(ad);

        // Проверить, что объект ExtendedAdDto создан успешно
        assertNotNull(extendedAdDto);
        // Добавить здесь необходимые проверки значений
    }

    @Test
    public void testGetAds() {
        // Создать список объявлений
        List<Ad> ads = new ArrayList<>();
        ads.add(new Ad());
        ads.add(new Ad());

        // Вызвать метод getAds
        AdsDto adsDto = adMapper.getAds(ads);

        // Проверить, что объект AdsDto создан успешно и содержит верные значения
        assertNotNull(adsDto);
        assertEquals(2, adsDto.getCount());
        // Добавить здесь дополнительные проверки на корректность результатов
    }
}
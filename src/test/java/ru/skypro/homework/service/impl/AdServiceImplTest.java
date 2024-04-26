package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.AdMapperImpl;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.ImageNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {
//    @Mock
//    private AdRepository adRepository;
//    @Mock
//    private ImageService imageService;
//    @Spy
//    private AdMapper adMapper = new AdMapperImpl();
    @InjectMocks
    private AdServiceImpl adService;

    @Mock
    private AdRepository adRepository;

    @Mock
    private UserService userService;

    @Mock
    private ImageService imageService;

    @Mock
    private AdMapper adMapper;

    private List<Ad> adsList;
    private Ad ads1;
    private Ad ads2;
    private CreateOrUpdateAdDto createOrUpdateAdDto;
    private User testUser;
    private Image testImage;
//    @BeforeEach
//    void init() {
//        testUser = new User();
//        testUser.setId(50);
//        testUser.setUsername("test@test.com");
//        //auth = new UsernamePasswordAuthenticationToken(testUser, null);
//
//        testImage = new Image();
//        testImage.setId(1L);
//
//        createOrUpdateAdDto = new CreateOrUpdateAdDto();
//        createOrUpdateAdDto.setDescription("Test description");
//        createOrUpdateAdDto.setTitle("Test title");
//        createOrUpdateAdDto.setPrice(500);
//
//        ads1 = new Ad();
//        ads1.setId(1);
//        ads1.setPrice(new BigDecimal(100));
//        ads1.setTitle("Test ads");
//        ads1.setAuthor(testUser);
//
//        ads2 = new Ad();
//        ads2.setId(2);
//        ads2.setPrice(new BigDecimal(200));
//        ads2.setTitle("Test ads 2");
//        ads2.setAuthor(testUser);
//
//        adsList = List.of(ads1, ads2);
//    }

    @Test
    void testGetAllAds() {
        AdsDto expectedAdsDto = new AdsDto();
        Ad ad = new Ad();
        when(adRepository.findAll()).thenReturn(Collections.singletonList(ad));
        when(adMapper.getAds(Collections.singletonList(ad))).thenReturn(expectedAdsDto);

        AdsDto actualAdsDto = adService.getAllAds();

        assertEquals(expectedAdsDto, actualAdsDto);
    }

    @Test
    void testGetAdById_ThrowsAdNotFoundException() {
        when(adRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(AdNotFoundException.class, () -> adService.getAdById(1));
    }

    @Test
    void testGetAdById_ReturnsAd() {
        Ad expectedAd = new Ad();
        when(adRepository.findById(anyInt())).thenReturn(Optional.of(expectedAd));

        Ad actualAd = adService.getAdById(4);

        assertEquals(expectedAd, actualAd);
    }

    @Test
    void creatAd() {
    }

    @Test
    void getExtendedAdDto() {
    }

    @Test
    void updateAd() {
    }

    @Test
    void getAdsDtoMe() {
    }

    @Test
    void deletedAd() {
    }

    @Test
    void updateImage() {
    }

    @Test
    void getAdImage() {
    }

    @Test
    void getAdById() {
    }
}
package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skypro.homework.component.UserSecurity;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapperImpl;
import ru.skypro.homework.mapper.CommentMapperImpl;
import ru.skypro.homework.mapper.UserMapperImpl;
import ru.skypro.homework.repository.*;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.DataForTests.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AdServiceImplTest {
    @Autowired
    private AdServiceImpl adService;

    @MockBean
    private AdRepository adsRepository;
    @MockBean
    private AuthoritiesRepository authorityRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private ImageRepository imageRepository;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserSecurity userSecurity;
    @MockBean
    private PasswordEncoder encoder;
    @MockBean
    private Authentication authentication;

    @SpyBean
    private AuthorityServiceImpl authorityService;
    @SpyBean
    private ImageServiceImpl imageService;
    @SpyBean
    private UserServiceImpl userService;

    @SpyBean
    private AdMapperImpl adsMapper;
    @SpyBean
    private CommentMapperImpl commentMapper;
    @SpyBean
    private UserMapperImpl userMapper;

    @BeforeEach
    void setUp() {
        when(adsRepository.findAll()).thenReturn(getDefaultAdList());
        when(adsRepository.save(any())).thenReturn(getDefaultAd());
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(getDefaultUser()));
        when(adsRepository.findById(any())).thenReturn(Optional.of(getDefaultAd()));
        when(imageRepository.findById(any())).thenReturn(Optional.of(getDefaltImage()));
        when(adsRepository.findByAuthorId(anyInt())).thenReturn(Optional.of(getDefaultAdList()));
    }

    @Test
    void getAllAds() {
        assertEquals(adsMapper.getAds(getDefaultAdList()), adService.getAllAds());
    }

    @Test
    void creatAd() {
        MockMultipartFile imagePartFile = new MockMultipartFile("image",
                "Ad.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                getDefaultImageData());

        doReturn(getDefaltImage()).when(imageService).addImage(imagePartFile);

        Ad actualAd = getDefaultAd();

        CreateOrUpdateAdDto createOrUpdateAdDto = new CreateOrUpdateAdDto();
        createOrUpdateAdDto.setTitle(actualAd.getTitle());
        createOrUpdateAdDto.setDescription(actualAd.getDescription());
        createOrUpdateAdDto.setPrice(actualAd.getPrice().intValue());

        assertEquals(adsMapper.adToAdDto(getDefaultAd()), adService.creatAd(createOrUpdateAdDto, imagePartFile, authentication));
    }

    @Test
    void getExtendedAdDto() {
        assertEquals(adsMapper.adToExtendedAdDto(getDefaultAd()), adService.getExtendedAdDto(1));
    }

    @Test
    void updateAd() {
        CreateOrUpdateAdDto createOrUpdateAdDto = new CreateOrUpdateAdDto();

        createOrUpdateAdDto.setTitle("Updated ad title");
        createOrUpdateAdDto.setDescription("Updated ad description");
        createOrUpdateAdDto.setPrice(192);

        Ad ad = adService.getAdById(1);
        Ad infoToUpdate = adsMapper.createAdsDtoToAds(createOrUpdateAdDto);
        ad.setPrice(infoToUpdate.getPrice());
        ad.setTitle(infoToUpdate.getTitle());
        ad.setDescription(infoToUpdate.getDescription());

        assertEquals(adsMapper.adToAdDto(ad), adService.updateAd(1, createOrUpdateAdDto));
    }

    @Test
    void getAdsDtoMe() {
        assertEquals(adsMapper.getAds(getDefaultAdList()), adService.getAdsDtoMe(getDefaultUser().getUsername()));
    }

    @Test
    void deletedAd() {

    }

    @Test
    void updateImage() {
        doReturn(getDefaultImageData()).when(imageService).getImageData(1);
        doReturn(getSecondDefaultImageData()).when(imageService).getImageData(2);

        log.info(new String(adService.getAdImage(1)));
        assertArrayEquals(getDefaultImageData(), adService.getAdImage(1));

        MockMultipartFile imagePartFile = new MockMultipartFile("image",
                "Ad.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                getSecondDefaultImageData());

        doReturn(getSecondDefaltImage()).when(imageService).addImage(imagePartFile);
        getDefaultAd().setImage(getSecondDefaltImage());

        byte[] updatedImageData = adService.updateImage(1, imagePartFile);
        log.info(new String(updatedImageData));
        assertArrayEquals(getSecondDefaultImageData(), updatedImageData);
    }

    @Test
    void getAdImage() {
        doReturn(getDefaultImageData()).when(imageService).getImageData(1);

        assertArrayEquals(getDefaultImageData(), adService.getAdImage(1));
    }

    @Test
    void getAdById() {
        assertEquals(getDefaultAd(), adService.getAdById(1));
    }
}
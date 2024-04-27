package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.component.UserSecurity;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapperImpl;
import ru.skypro.homework.mapper.CommentMapperImpl;
import ru.skypro.homework.mapper.UserMapperImpl;
import ru.skypro.homework.repository.*;
import ru.skypro.homework.service.impl.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdsApiController.class)
class AdsApiControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @MockBean
    private ImageRepository imageRepository;
    @MockBean
    private AdRepository adsRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private AuthoritiesRepository authorityRepository;
    @MockBean
    private UserRepository usersRepository;
    @MockBean
    private UserSecurity userSecurity;
    @MockBean
    private PasswordEncoder encoder;
    @SpyBean
    private AdServiceImpl adsService;
    @MockBean
    private UserServiceImpl userService;
    @SpyBean
    private AdMapperImpl adsMapper;
    @SpyBean
    private CommentMapperImpl commentMapper;
    @SpyBean
    private UserMapperImpl userMapper;
    @SpyBean
    private AuthorityServiceImpl authorityService;
    @SpyBean
    private CommentServiceImpl commentService;
    @SpyBean
    private ImageServiceImpl imageService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        when(userSecurity.isAdsAuthor(any())).thenReturn(true);
        when(userSecurity.isCommentAuthor(any())).thenReturn(true);
    }

    @Test
    public void addAdTest() {

    }

    @Test
    @WithMockUser
    public void getAdByIdTest() throws Exception {
        ExtendedAdDto extendedAdDto = new ExtendedAdDto();
        extendedAdDto.setPk(1);
        extendedAdDto.setTitle("Test Ad");
        extendedAdDto.setDescription("This is a test ad");

        when(adsService.getExtendedAdDto(1)).thenReturn(extendedAdDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk", is(1)))
                .andExpect(jsonPath("$.title", is("Test Ad")))
                .andExpect(jsonPath("$.description", is("This is a test ad")));
    }

    @Test
    @WithMockUser(username = "1@gmail.com", roles = "ADMIN")
    public void getAdsMeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ads/me")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.results", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    @WithMockUser
    public void getAdsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ads")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.results", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    @WithMockUser(username = "1", roles = "ADMIN")
    public void removeAdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/ads/{id}", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "1", roles = "ADMIN")
    public void updateAdsTest() throws Exception {
        CreateOrUpdateAdDto adDto = new CreateOrUpdateAdDto();
        adDto.setTitle("Updated Ad");
        adDto.setDescription("This is an updated ad");

        User user = new User();
        user.setEmail("user@gmail.com");
        user.setId(1);

        when(userService.getUser("User")).thenReturn(user);

        Ad ad = adsMapper.createAdsDtoToAds(adDto);
        ad.setId(1);
        ad.setAuthor(userService.getUser("User"));
        AdDto resultAdDto = adsMapper.adToAdDto(ad);

        when(adsService.updateAd(1, adDto)).thenReturn(resultAdDto);

        ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/{id}", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(adDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void getImageTest() {

    }

    @Test
    @WithMockUser
    void updateImageTest() {

    }

    @Test
    @WithMockUser
    public void getCommentsByAdIdTest() {

    }

    @Test
    @WithMockUser
    public void addCommentTest() {

    }

    @Test
    @WithMockUser
    public void updateCommentTest() {
    }

    @Test
    @WithMockUser
    public void deleteComment() {

    }
}
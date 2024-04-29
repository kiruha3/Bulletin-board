package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.component.UserSecurity;
import ru.skypro.homework.dto.ad.ExtendedAdDto;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapperImpl;
import ru.skypro.homework.mapper.CommentMapperImpl;
import ru.skypro.homework.mapper.UserMapperImpl;
import ru.skypro.homework.repository.*;
import ru.skypro.homework.service.impl.*;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.homework.DataForTests.*;

@WebMvcTest(AdsApiController.class)
class AdsApiControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @MockBean
    private AdRepository adsRepository;
    @MockBean
    private AuthoritiesRepository authorityRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private ImageRepository imageRepository;
    @MockBean
    private UserRepository usersRepository;

    @MockBean
    private UserSecurity userSecurity;
    @MockBean
    private PasswordEncoder encoder;

    @SpyBean
    private AdServiceImpl adsService;
    @SpyBean
    private AuthorityServiceImpl authorityService;
    @SpyBean
    private CommentServiceImpl commentService;
    @SpyBean
    private ImageServiceImpl imageService;
    @MockBean
    private UserServiceImpl userService;

    @SpyBean
    private AdMapperImpl adsMapper;
    @SpyBean
    private CommentMapperImpl commentMapper;
    @SpyBean
    private UserMapperImpl userMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        when(userSecurity.isAdsAuthor(any())).thenReturn(true);
        when(userSecurity.isCommentAuthor(any())).thenReturn(true);
        when(adsRepository.findById(anyInt())).thenReturn(Optional.of(getDefaultAd()));
        when(adsRepository.save(any())).thenReturn(getDefaultAd());
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(getDefaltImage()));
        when(userService.getUser(any())).thenReturn(getDefaultUser());
        when(adsRepository.findByAuthorId(anyInt())).thenReturn(Optional.of(new ArrayList<>()));
        when(commentRepository.save(any())).thenReturn(getDefaultComment());
        when(usersRepository.findById(any())).thenReturn(Optional.of(getDefaultUser()));
        when(commentRepository.findAllByAdId(anyInt())).thenReturn(Optional.of(new ArrayList<>()));
    }

    @Test
    @WithMockUser(username = "1@gmail.com", roles = "ADMIN")
    public void addAdTest() throws Exception {
        Ad actualAd = getDefaultAd();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        MockMultipartFile imagePartFile = new MockMultipartFile("image",
                "Ad.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                getDefaultImageData());

        MockPart propertiesPart = new MockPart("properties", mapper.writeValueAsString(getDefaultAd()).getBytes());
        propertiesPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        doReturn(getDefaltImage()).when(imageService).addImage(imagePartFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/ads")
                        .file(imagePartFile)
                        .part(propertiesPart)
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author", is(actualAd.getAuthor().getId())))
                .andExpect(jsonPath("$.image", is(imageService.getAdImageUrl(1))))
                .andExpect(jsonPath("$.pk", is(actualAd.getId())))
                .andExpect(jsonPath("$.price", is(actualAd.getPrice().intValue())))
                .andExpect(jsonPath("$.title", is(actualAd.getTitle())));
    }

    @Test
    @WithMockUser
    public void getAdByIdTest() throws Exception {
        ExtendedAdDto extendedAdDto = adsMapper.adToExtendedAdDto(getDefaultAd());

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk", is(extendedAdDto.getPk())))
                .andExpect(jsonPath("$.authorFirstName", is(extendedAdDto.getAuthorFirstName())))
                .andExpect(jsonPath("$.authorLastName", is(extendedAdDto.getAuthorLastName())))
                .andExpect(jsonPath("$.description", is(extendedAdDto.getDescription())))
                .andExpect(jsonPath("$.email", is(extendedAdDto.getEmail())))
                .andExpect(jsonPath("$.image", is(extendedAdDto.getImage())))
                .andExpect(jsonPath("$.phone", is(extendedAdDto.getPhone())))
                .andExpect(jsonPath("$.price", is(extendedAdDto.getPrice())))
                .andExpect(jsonPath("$.title", is(extendedAdDto.getTitle())));
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
    public void updateAdsTest() {
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
    public void getCommentsByAdIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ads/1/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(0)))
                .andExpect(jsonPath("$.results", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    @WithMockUser
    public void addCommentTest() throws Exception {
        CreateOrUpdateCommentDto createOrUpdateCommentDto = new CreateOrUpdateCommentDto();
        createOrUpdateCommentDto.setText(getDefaultComment().getText());
        ObjectMapper objectMapper = new ObjectMapper();

        CommentDto actualComment = commentMapper.commentToCommentDto(getDefaultComment());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/ads/1/comments")
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDto))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pk", is(actualComment.getPk())))
                .andExpect(jsonPath("$.author", is(actualComment.getAuthor())))
                .andExpect(jsonPath("$.authorImage", is(actualComment.getAuthorImage())))
                .andExpect(jsonPath("$.authorFirstName", is(actualComment.getAuthorFirstName())))
                .andExpect(jsonPath("$.createdAt", is(actualComment.getCreatedAt())))
                .andExpect(jsonPath("$.text", is(createOrUpdateCommentDto.getText())));
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
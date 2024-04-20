package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UploadFileException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.utils.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final String imageSaveDirectory;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;

    public ImageServiceImpl(@Value("${images.save.directory:./images}") String imageSaveDirectory) {
        this.imageSaveDirectory = imageSaveDirectory;

        Path imageSaveDirectoryPath = Path.of(imageSaveDirectory);
        if (!Files.exists(imageSaveDirectoryPath)) {
            try {
                Files.createDirectories(imageSaveDirectoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Image addImage(MultipartFile imageMultipart) {
        Image image = imageRepository.save(new Image());

        try {
            Path newImageFilePath = Path.of(imageSaveDirectory, image.getId() + "." + FilenameUtils.getExtension(imageMultipart.getOriginalFilename()).get());

            FileOutputStream fileOutputStream = new FileOutputStream(newImageFilePath.toFile());
            imageMultipart.getInputStream().transferTo(fileOutputStream);
            fileOutputStream.close();

            image.setFilePath(newImageFilePath.toFile().getAbsolutePath());
            image.setMediaType(imageMultipart.getContentType());
        } catch (IOException e) {
            throw new UploadFileException();
        }

        imageRepository.save(image);

        return image;
    }

    @Override
    public Image getImage(long id) {
        return imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
    }

    @Override
    public byte[] getImageData(long id) {
        try {
            FileInputStream fileInputStream = new FileInputStream(getImage(id).getFilePath());
            BufferedInputStream bis = new BufferedInputStream(fileInputStream);

            byte[] imageData = bis.readAllBytes();

            bis.close();
            fileInputStream.close();

            return imageData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAdImageUrl(int adId) {
        return "/ads/" + adId + "/image";
    }

    @Override
    public String getUserImageUrl(int userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (user.getImage() == null) {
            return null;
        }

        return "/users/" + userId + "/image";
    }
}

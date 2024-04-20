package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.exception.ImageCanNotBeReadException;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.exception.EmptyFileException;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public Image createImage(MultipartFile file, Ad ad) {
        log.info("Был вызван метод createImage из {}", ImageService.class.getSimpleName());
        Image imageToSave = new Image();
        extractInfoFromFile(file, imageToSave);
        imageToSave.setAd(ad);
        return imageRepository.save(imageToSave);
    }

    @Override
    public byte[] getAdImage(Long id) {
        return getImage(id).getData();
    }

    @Override
    public Image getImage(long id) {
        return imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
    }

    private void extractInfoFromFile(MultipartFile file, Image imageToSave) {
        if (file.isEmpty()) {
            log.warn("Файл '{}' пуст!", file.getOriginalFilename());
            throw new EmptyFileException();
        }
        byte[] imageData;
        try {
            imageData = file.getBytes();
        } catch (IOException e) {
            log.error("Файл \"{}\" имеет некоторые проблемы и не может быть прочитан.", file.getOriginalFilename());
            throw new ImageCanNotBeReadException("Проблемы с загруженным изображением");
        }
        imageToSave.setData(imageData);
        imageToSave.setFileSize(file.getSize());
        imageToSave.setMediaType(file.getContentType());
    }
}

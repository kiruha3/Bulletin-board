package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query("SELECT s from Ad s")
    List<Image> getAll();
}

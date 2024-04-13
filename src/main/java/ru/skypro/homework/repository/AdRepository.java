package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.Ad;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad,Long> {
    @Query("SELECT s from Ad s")
    List<Ad> getAll();
}

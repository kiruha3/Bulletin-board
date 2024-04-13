package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {
    @Query("SELECT s from Ad s")
    List<User> getAll();
}

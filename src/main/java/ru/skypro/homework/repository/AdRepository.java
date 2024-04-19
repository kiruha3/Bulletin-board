package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad,Integer> {
    @Query("SELECT s from Ad s")
    List<Ad> getAll();
 //   Ad findByPk(int adId);
 //   List<Ad> findAllByUser(User user);
    List<Ad> findAdsByAuthorUsername(String email);
}

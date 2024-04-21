package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Authorities;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
}

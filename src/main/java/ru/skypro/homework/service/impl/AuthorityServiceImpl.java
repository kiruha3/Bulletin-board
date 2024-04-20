package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Authorities;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AuthoritiesRepository;
import ru.skypro.homework.service.AuthoritiesService;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthoritiesService {
    private final AuthoritiesRepository authoritiesRepository;

    @Override
    public void addAuthorities(User user) {
        Authorities authorities = new Authorities();
        authorities.setUsername(user.getUsername());
        authorities.setAuthority(user.getRole().name());

        authoritiesRepository.save(authorities);
    }
}

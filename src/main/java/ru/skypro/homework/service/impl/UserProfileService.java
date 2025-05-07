package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

@Service
public class UserProfileService {

    private final UserRepository repository;

    public UserProfileService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity createUser(UserEntity user) {
        return repository.save(user);
    }

    public UserEntity createUser(Register register) {
        return createUser(new UserEntity(
                register.getUsername(),
                register.getFirstName(),
                register.getLastName(),
                register.getPhone(),
                register.getRole()));
    }
}

package ru.skypro.homework.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapperImpl;
import ru.skypro.homework.repository.UserRepository;

@Service
public class UserProfileService {

    private final UserRepository repository;
    private final JdbcUserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserMapperImpl mapper;

    public UserProfileService(UserRepository repository,
                              JdbcUserDetailsManager manager,
                              PasswordEncoder encoder,
                              UserMapperImpl mapper) {
        this.repository = repository;
        this.manager = manager;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    public UserEntity createUser(UserEntity user) {
        return repository.save(user);
    }

    public UserEntity createUser(Register register) {
        return this.createUser(new UserEntity(
                register.getUsername(),
                register.getFirstName(),
                register.getLastName(),
                register.getPhone(),
                register.getRole()));
    }

    public boolean changePassword(NewPassword newPassword) {
        try {
            manager.changePassword(newPassword.getCurrentPassword(), encoder.encode(newPassword.getCurrentPassword()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public User getUserDTOByUsername(String username) {
        UserEntity entity = repository.findByUsername(username).get();
        return mapper.toDto(entity);
    }

    public UserEntity findUserByUsername(String username) {
        return repository.findByUsername(username).get();
    }

    public UpdateUser updateUserByUsername(String username, UpdateUser user) {
        UserEntity entity = repository.findByUsername(username).get();
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPhone(user.getPhone());
        repository.save(entity);
        return user;
    }
}

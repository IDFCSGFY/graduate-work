package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;

/**
 * Сервис-класс для аутентификации пользователей.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final JdbcUserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserProfileService profileService;

    public AuthServiceImpl(JdbcUserDetailsManager manager,
                           PasswordEncoder passwordEncoder,
                           UserProfileService profileService) {
        this.manager = manager;
        this.encoder = passwordEncoder;
        this.profileService = profileService;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (manager.userExists(register.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(register.getPassword())
                        .username(register.getUsername())
                        .roles(register.getRole().name())
                        .build());
        profileService.createUser(register);
        return true;
    }
}

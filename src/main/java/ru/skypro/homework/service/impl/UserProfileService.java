package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapperImpl;
import ru.skypro.homework.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис-класс для менеджмента информации профиля пользователей.
 * Контекстуальные классы:
 *
 * @see User
 * @see UserEntity
 */
@Service
public class UserProfileService {

    @Value("${avatar.img.dir.path}")
    private String imgDirPath;

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

    /**
     * Сохраняет пользователя в БД по объекту UserEntity.
     *
     * @param user объект UserEntity для сохранения.
     * @return {@code UserEntity} – сохранённый в БД пользователь.
     * @see UserEntity
     */
    public UserEntity createUser(UserEntity user) {
        return repository.save(user);
    }

    /**
     * Сохраняет пользователя в БД по объекту Register.
     *
     * @param register DTO-объект хранящий информацию о пользователе для сохранения.
     * @return {@code UserEntity} – сохранённый в БД пользователь.
     * @see UserEntity
     * @see Register
     */
    public UserEntity createUser(Register register) {
        return this.createUser(new UserEntity(
                register.getUsername(),
                register.getFirstName(),
                register.getLastName(),
                register.getPhone(),
                register.getRole()));
    }

    /**
     * Меняет пароль аутентифицированного пользователя на указанный новый.
     *
     * @param newPassword DTO-объект хранящий текущий и новый пароль.
     * @return {@code boolean} – успешность операции по смене пароля.
     * @see NewPassword
     */
    public boolean changePassword(NewPassword newPassword) {
        try {
            manager.changePassword(newPassword.getCurrentPassword(), encoder.encode(newPassword.getCurrentPassword()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Возвращает из БД мапнутый в DTO объект пользователя User по имени пользователя.
     *
     * @param username юзернейм пользователя для поиска.
     * @return {@code User} – DTO-объект с информацией о пользователе.
     * @see User
     */
    public User getUserDTOByUsername(String username) {
        UserEntity entity = repository.findByUsername(username).get();
        return mapper.toDto(entity);
    }

    /**
     * Возвращает из БД объект пользователя UserEntity по имени пользователя.
     *
     * @param username юзернейм пользователя для поиска.
     * @return {@code UserEntity} – DTO-объект с информацией о пользователе.
     * @see UserEntity
     */
    public UserEntity findUserByUsername(String username) {
        return repository.findByUsername(username).get();
    }

    /**
     * Обновляет персональную информацию об аутентифицированном пользователе на указанную новую.
     *
     * @param username юзернейм аутентифицированного пользователя.
     * @param user     актуальная информация о пользователе.
     * @return {@code UpdateUser} – DTO-объект из которого была взята актуальная информация.
     * @see UpdateUser
     */
    public UpdateUser updateUserByUsername(String username, UpdateUser user) {
        UserEntity entity = repository.findByUsername(username).get();
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPhone(user.getPhone());
        repository.save(entity);
        return user;
    }

    /**
     * Загрузка аватара пользователя.
     *
     * @param authentication информация об аутентифицированном пользователе для запроса в БД.
     * @param image          изображение нового аватара пользователя.
     */
    public void uploadImage(Authentication authentication, MultipartFile image) {
        UserEntity user = repository.findByUsername(authentication.getName()).get();
        String imageName = image.getOriginalFilename();
        Path path = Path.of(imgDirPath, user.getId() + "." + imageName.substring(imageName.indexOf(".") + 1));
        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
            bis.transferTo(bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        user.setImage(path.toString());
        repository.save(user);
    }
}

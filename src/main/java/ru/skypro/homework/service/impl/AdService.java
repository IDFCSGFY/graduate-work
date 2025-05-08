package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdMapperImpl;
import ru.skypro.homework.repository.AdRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис-класс для менеджмента объявлений.
 * Контекстуальные классы:
 *
 * @see Ad
 * @see AdEntity
 */
@Service
public class AdService {

    @Value("${avatar.img.dir.path}")
    String imgDirPath;

    private final AdRepository repository;
    private final UserProfileService userProfileService;
    private final JdbcUserDetailsManager manager;
    private final AdMapperImpl mapper;

    public AdService(AdRepository repository,
                     UserProfileService userProfileService,
                     JdbcUserDetailsManager manager,
                     AdMapperImpl mapper) {
        this.repository = repository;
        this.userProfileService = userProfileService;
        this.manager = manager;
        this.mapper = mapper;
    }

    /**
     * Возвращает полный список всех объявлений.
     *
     * @return {@code Ads} – DTO-объект, содержащий список полученных объявлений.
     * @see Ads
     */
    public Ads findAllAds() {
        List<AdEntity> entityList = repository.findAll();
        return this.adsFiller(entityList);
    }

    /**
     * Сохраняет новое объявление в БД.
     *
     * @param authentication информация об аутентифицированном пользователе.
     * @param ad
     * @param image
     * @return {@code Ad} – DTO-объект созданного объявления.
     * @see Ad
     */
    public Ad createAd(Authentication authentication, Ad ad, MultipartFile image) {
        AdEntity entity = mapper.toEntity(ad);
        entity.setId(0);
        entity.setAuthor(userProfileService.findUserByUsername(authentication.getName()));
        String imgName = image.getOriginalFilename();
        Path path = Path.of(imgDirPath, entity.getAuthor().getId() + "." + imgName.substring(imgName.indexOf(".") + 1));
        try {
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        saveFile(image, path);
        entity.setImage(path.toString());
        return mapper.toDto(repository.save(entity));
    }

    /**
     * Возвращает объявление по указанному {@code id}.
     *
     * @param id идентификатор объявления.
     * @return {@code AdEntity} – entity-объект найденного объявления.
     * @see AdEntity
     */
    public AdEntity findAdById(Integer id) {
        adValidationCheck(id);
        return repository.findById(id).get();
    }

    /**
     * Возвращает полную информацию об объявлении по указанному {@code id}.
     *
     * @param id идентификатор объявления.
     * @return {@code ExtendedAd} – DTO-объект, содержащий полную информацию о найденном объявлении.
     * @see ExtendedAd
     */
    public ExtendedAd findExtendedAdById(Integer id) {
        this.adValidationCheck(id);
        AdEntity adEntity = repository.findById(id).get();
        UserEntity userEntity = userProfileService.findUserByUsername(adEntity.getAuthor().getUsername());
        return new ExtendedAd(
                adEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                adEntity.getDescription(),
                userEntity.getUsername(),
                adEntity.getImage(),
                userEntity.getPhone(),
                adEntity.getPrice(),
                adEntity.getTitle()
        );
    }

    /**
     * Удаляет объявление по указанному {@code id}.
     *
     * @param authentication информация об аутентифицированном пользователе.
     * @param id             идентификатор объявления.
     */
    public void deleteAdById(Authentication authentication, Integer id) {
        this.adValidationCheck(id);
        this.initiatorValidationCheck(authentication, id);
        repository.deleteById(id);
    }

    /**
     * Обновляет информацию об объявлении по указанному {@code id}.
     *
     * @param authentication информация об аутентифицированном пользователе.
     * @param id             идентификатор объявления.
     * @param ad             новая информация объявления.
     * @return {@code Ad} – DTO-объект, содержащий обновлённую информацию объявления.
     * @see Ad
     * @see CreateOrUpdateAd
     */
    public Ad updateAdById(Authentication authentication, Integer id, CreateOrUpdateAd ad) {
        this.adValidationCheck(id);
        this.initiatorValidationCheck(authentication, id);

        AdEntity entity = repository.findById(id).get();
        entity.setTitle(ad.getTitle());
        entity.setPrice(ad.getPrice());
        entity.setDescription(ad.getDescription());
        return mapper.toDto(repository.save(entity));
    }

    /**
     * Возвращает полный список объявлений авторства аутентифицированного пользователя.
     *
     * @param authentication информация об аутентифицированном пользователе.
     * @return {@code Ads} – DTO-объект, содержащий список полученных объявлений.
     * @see Ads
     */
    public Ads findAllAdsByUser(Authentication authentication) {
        Integer userId = userProfileService.findUserByUsername(authentication.getName()).getId();
        List<AdEntity> entityList = repository.findAllByAuthorId(userId);
        return this.adsFiller(entityList);
    }

    /**
     * Обновляет изображение объявления.
     *
     * @param authentication информация об аутентифицированном пользователе.
     * @param id             идентификатор объявления.
     * @param image          новое изображение объявления.
     * @return {@code byte[]} – сохранённое изображение в строке байтов.
     */
    public byte[] updateAdImage(Authentication authentication, Integer id, MultipartFile image) {
        this.adValidationCheck(id);
        this.initiatorValidationCheck(authentication, id);
        String pathStr = userProfileService.findUserByUsername(authentication.getName()).getImage();
        Path path = Path.of(pathStr);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return saveFile(image, path);
    }

    private Ads adsFiller(List<AdEntity> entityList) {
        Ads ads = new Ads();
        ads.setResults(entityList.stream().map(mapper::toDto).collect(Collectors.toList()));
        ads.setCount(ads.getResults().size());
        return ads;
    }

    private byte[] saveFile(MultipartFile image, Path path) {
        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            bis.transferTo(bos);
            bis.transferTo(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initiatorValidationCheck(Authentication authentication, Integer id) {
        Integer initiatorId = userProfileService.findUserByUsername(authentication.getName()).getId();
        Integer adAuthorId = repository.findById(id).get().getAuthor().getId();

        UserDetails details = manager.loadUserByUsername(authentication.getName());
        String initiatorRole = details.getAuthorities().stream().limit(1).toString();

        if (!adAuthorId.equals(initiatorId) || initiatorRole.equals("ROLE_ADMIN")) {
            throw new IllegalArgumentException();
        }
    }

    private void adValidationCheck(Integer id) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}

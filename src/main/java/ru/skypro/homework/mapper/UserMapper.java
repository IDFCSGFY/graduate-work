package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "email", source = "entity.username")
    User toDto(UserEntity entity);

    @Mapping(target = "username", source = "dto.email")
    UserEntity toEntity(User dto);
}

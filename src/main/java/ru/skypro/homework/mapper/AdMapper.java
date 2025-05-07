package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.AdEntity;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "id", target = "pk")
    Ad toDto(AdEntity entity);

    @Mapping(source = "author", target = "author.id")
    @Mapping(source = "pk", target = "id")
    AdEntity toEntity(Ad dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "author", ignore = true)
    AdEntity fromCreateDto(CreateOrUpdateAd dto);
}

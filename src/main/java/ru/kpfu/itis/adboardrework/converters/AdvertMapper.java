package ru.kpfu.itis.adboardrework.converters;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import ru.kpfu.itis.adboardrework.dto.advert.AdvertDto;
import ru.kpfu.itis.adboardrework.models.Advert;

import java.util.List;


@Mapper(uses = {AdvertMapper.class}, componentModel = "spring")
public interface AdvertMapper {
    AdvertDto toDto(Advert advert);
    List<AdvertDto> toDtoList(Iterable<Advert> advert);
}

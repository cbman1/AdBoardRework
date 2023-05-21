package ru.kpfu.itis.adboardrework.converters;

import org.mapstruct.Mapper;
import ru.kpfu.itis.adboardrework.dto.advert.AdvertDto;
import ru.kpfu.itis.adboardrework.models.Advert;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AdvertMapper {
    AdvertDto toDto(Advert advert);
    List<AdvertDto> toDtoList(Iterable<Advert> advert);
}

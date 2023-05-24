package ru.kpfu.itis.adboardrework.services;

import ru.kpfu.itis.adboardrework.dto.advert.AdvertDto;
import ru.kpfu.itis.adboardrework.dto.advert.UpdateAdvertDto;
import ru.kpfu.itis.adboardrework.models.Advert;

import java.security.Principal;
import java.util.List;

public interface AdvertService {
    void addAdvert(AdvertDto advertDto, Principal principal);
    AdvertDto getAdvertDtoById(Long id);
    Advert getAdvertById(Long id);
    Long getIdLastAdvert(Principal principal);
    boolean checkFavoriteUser(Principal principal, Long idAdvert);
    void addAdvertFavorite(Long id, Principal principal);
    void deleteAdvertFavorite(Long id, Principal principal);
    void advertSold(Long id, Principal principal);

    void returnInSell(Long id, Principal principal);
    List<AdvertDto> getAllAdvertsDtoByUser(Long idUser);
    List<Advert> getAllActiveAdverts();
    List<Advert> getAllActiveAdvertsByUser(Long idUser);
    List<Advert> getAllSoldAdvertsByUser(Long idUser);
    void updateAdvert(Long id, UpdateAdvertDto updateAdvertDto);
}

package ru.kpfu.itis.adboardrework.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.adboardrework.converters.AdvertMapper;
import ru.kpfu.itis.adboardrework.dto.advert.AdvertDto;
import ru.kpfu.itis.adboardrework.dto.advert.UpdateAdvertDto;
import ru.kpfu.itis.adboardrework.models.Advert;
import ru.kpfu.itis.adboardrework.models.State;
import ru.kpfu.itis.adboardrework.models.User;
import ru.kpfu.itis.adboardrework.repositories.AdvertRepository;
import ru.kpfu.itis.adboardrework.repositories.UserRepository;
import ru.kpfu.itis.adboardrework.services.AdvertService;
import ru.kpfu.itis.adboardrework.exceptions.NotFoundException;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final AdvertMapper advertMapper;


    @Override
    public void addAdvert(AdvertDto advertDto, Principal principal) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String nowDate = simpleDateFormat.format(date);

        User thisUser = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new NotFoundException("not found"));
        Advert newAdvert = advertRepository.save(Advert.builder()
                .name(advertDto.getName())
                .description(advertDto.getDescription())
                .price(advertDto.getPrice())
                .authorId(thisUser)
                .category(advertDto.getCategory())
                .coordinates(advertDto.getCoordinates())
                .salesStartDate(nowDate)
                .images(advertDto.getImages())
                .state(State.ACTIVE).build());
    }

    @Override
    public AdvertDto getAdvertDtoById(Long id) {
        return advertMapper.toDto(getAdvertOrThrow(id));
    }

    @Override
    public Advert getAdvertById(Long id) {
        return getAdvertOrThrow(id);
    }

    @Override
    public Long getIdLastAdvert(Principal principal) {
        User thisUser = getUserOrThrow(principal);
        return advertRepository.getMaxId(thisUser);
    }

    @Override
    public boolean checkFavoriteUser(Principal principal, Long idAdvert) {
        User thisUser = getUserOrThrow(principal);
        Advert advert = getAdvertOrThrow(idAdvert);

        return checkFavUser(thisUser, advert);
    }

    @Override
    public void addAdvertFavorite(Long id, Principal principal) {
        User thisUser = getUserOrThrow(principal);
        Advert advert = getAdvertOrThrow(id);
        if (!checkFavUser(thisUser, advert)) {
            thisUser.getFavorites().add(advert);
            userRepository.save(thisUser);
        }
    }

    @Override
    public void deleteAdvertFavorite(Long id, Principal principal) {
        User thisUser = getUserOrThrow(principal);
        Advert advert = getAdvertOrThrow(id);
        if (checkFavUser(thisUser, advert)) {
            System.out.println(thisUser.getFavorites().size());
            thisUser.getFavorites().remove(advert);
            System.out.println(thisUser.getFavorites().size());
            userRepository.save(thisUser);
        }
    }


    private boolean checkFavUser(User user, Advert advert) {
        return user.getFavorites().contains(advert);
    }

    @Override
    public void advertSold(Long id, Principal principal) {
        Advert advert = getAdvertOrThrow(id);
        User thisUser = getUserOrThrow(principal);
//        if (advert.getAuthorId().equals(thisUser)) {
            if (advert.getState().equals(State.ACTIVE)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String nowDate = simpleDateFormat.format(new Date());

                advert.setState(State.SOLD);
                advert.setDateOfSale(nowDate);

                advertRepository.save(advert);
            }
//        }
    }

    @Override
    public void returnInSell(Long id, Principal principal) {
        Advert advert = getAdvertOrThrow(id);
        User thisUser = getUserOrThrow(principal);

//        if (advert.getAuthorId().equals(thisUser)) {
            if (advert.getState().equals(State.SOLD)) {

                advert.setState(State.ACTIVE);
                advert.setDateOfSale(null);

                advertRepository.save(advert);
            }
//        }

    }

    @Override
    public List<AdvertDto> getAllAdvertsDtoByUser(Long id) {
        return advertMapper.toDtoList(advertRepository.findAllByAuthorId(getUserOrThrow(id)));
    }

    @Override
    public List<Advert> getAllActiveAdverts() {
        return advertRepository.findAllByState(State.ACTIVE);
    }

    @Override
    public List<Advert> getAllActiveAdvertsByUser(Long idUser) {
        return advertRepository.findAllByAuthorIdAndState(getUserOrThrow(idUser), State.ACTIVE);
    }

    @Override
    public List<Advert> getAllSoldAdvertsByUser(Long idUser) {
        return advertRepository.findAllByAuthorIdAndState(getUserOrThrow(idUser), State.SOLD);
    }

    private Advert getAdvertOrThrow(Long id) {
        return advertRepository.findById(id).orElseThrow(() -> new NotFoundException("not found"));
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("not found"));
    }

    private User getUserOrThrow(Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElseThrow(() -> new NotFoundException("not found"));
    }

    @Override
    public void updateAdvert(Long id, UpdateAdvertDto updateAdvertDto) {
        Advert advert = getAdvertById(id);

        if (!updateAdvertDto.getName().equals("")) {
            advert.setName(updateAdvertDto.getName());
        }
        if (!updateAdvertDto.getDescription().equals("")) {
            advert.setDescription(updateAdvertDto.getDescription());
        }
        if (updateAdvertDto.getPrice() != null) {
            advert.setPrice(updateAdvertDto.getPrice());
        }
        if (!updateAdvertDto.getImages().isEmpty()) {
            advert.setImages(updateAdvertDto.getImages());
        }

        advertRepository.save(advert);
    }
}

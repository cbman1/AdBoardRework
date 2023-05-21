package ru.kpfu.itis.adboardrework.dto.advert;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertDto {
    private String name;
    private String description;
    private Integer price;
    private String category;
    private String coordinates;
    private List<String> images = new ArrayList<>();
}

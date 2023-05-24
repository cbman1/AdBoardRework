package ru.kpfu.itis.adboardrework.dto.advert;


import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdvertDto {
    @Length(max = 100, message = "Name must be less than 100 characters")
    private String name;
    private String description;
    @PositiveOrZero(message = "Price >= 0")
    private Integer price;
    private List<String> images;
}

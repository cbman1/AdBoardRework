package ru.kpfu.itis.adboardrework.dto.advert;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertDto {
    @NotBlank(message = "Name is required")
    @Length(max = 100, message = "Name must be less than 100 characters")
    private String name;

    private String description;

    @PositiveOrZero(message = "Price >= 0")
    @NotNull(message = "Field price is required")
    private Integer price;
    @NotBlank(message = "Category is required")
    private String category;
    @NotBlank
    private String coordinates;

    private List<String> images = new ArrayList<>();
}

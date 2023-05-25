package ru.kpfu.itis.adboardrework.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewReviewDto {
    @NotBlank(message = "Field text is required")
    @Length(max=150, message = "Max length 150")
    private String text;
    @Range(min=1, max=5, message = "Score must be between 1 and 5")
    @NotNull(message = "Field score is required")
    private Integer score;
    @NotBlank
    private String advertName;
}

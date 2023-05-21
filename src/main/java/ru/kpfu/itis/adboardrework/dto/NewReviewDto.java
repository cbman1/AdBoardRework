package ru.kpfu.itis.adboardrework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewReviewDto {
    private String text;
    private Integer score;
    private String advertName;
}

package ru.kpfu.itis.adboardrework.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    @NotBlank
    @Length(max = 50)
    private String firstName;

    @NotBlank
    @Length(max = 50)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min=8, max = 50)
    private String password;

    @NotBlank
    @Pattern(regexp = "[+]?[0-9]{11}", message = "Phone number must be a format: +79999999999")
    private String phoneNumber;
}

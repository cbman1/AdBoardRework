package ru.kpfu.itis.adboardrework.dto.user;


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
    @NotBlank(message = "First name must not be empty")
    @Length(max = 50, message = "Max length is 50")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Length(max = 50, message = "Max length is 50 is")
    private String lastName;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Length(min=8, max = 50, message = "Min length is 8 and max length is 50")
    private String password;

    @NotBlank(message = "Phone number must not be empty")
    @Pattern(regexp = "[+]?[0-9]{11}", message = "Phone number must be a format: +79999999999")
    private String phoneNumber;
}

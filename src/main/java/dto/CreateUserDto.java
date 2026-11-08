package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    @NotBlank
    @NotNull
    @Email
    @Size(min = 12, max = 50)
    private String email;
}

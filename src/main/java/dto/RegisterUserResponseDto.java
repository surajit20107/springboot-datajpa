package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor 
public class RegisterUserResponseDto {

    private Long id;
    private String name;
    
}

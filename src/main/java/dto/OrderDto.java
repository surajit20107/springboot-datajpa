package dto;

import com.springDataJpa.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String productName;
    private User user;
}

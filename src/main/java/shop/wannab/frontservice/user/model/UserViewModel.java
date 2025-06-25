package shop.wannab.frontservice.user.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserViewModel {
    private String id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private LocalDate birth;
    private String nickname;
    private int points;
}

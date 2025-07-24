package shop.wannab.frontservice.user.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminPageUserResponse {
    private String name;
    private String nickname;
    private String email;
    private String phone;
    private LocalDate birth;
    private String username;
    private String role;
}
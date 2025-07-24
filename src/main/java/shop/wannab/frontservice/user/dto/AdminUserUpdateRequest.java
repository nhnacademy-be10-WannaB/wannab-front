package shop.wannab.frontservice.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AdminUserUpdateRequest {
    private String nickname;
    private String role;
}

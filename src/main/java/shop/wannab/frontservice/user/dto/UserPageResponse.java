package shop.wannab.frontservice.user.dto;

import java.time.LocalDate;

public record UserPageResponse(String name,
                               String nickname,
                               String email,
                               String phone,
                               LocalDate birth,
                               String username,
                               String password,
                               int points) {
}

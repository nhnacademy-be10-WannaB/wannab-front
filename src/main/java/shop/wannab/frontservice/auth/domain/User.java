package shop.wannab.frontservice.auth.domain;

import lombok.Data;

@Data
public class User {
    private Long userId;
    private String password;
    private String loginId;
    private Role role;
    private State state;
}

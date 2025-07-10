package shop.wannab.frontservice.auth;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shop.wannab.frontservice.auth.domain.State;
import shop.wannab.frontservice.auth.domain.User;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long id;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final State state;

    public CustomUserDetails(User user) {
        this.id = user.getUserId();
        this.username = user.getLoginId();
        this.password = user.getPassword();
        this.state = user.getState();
        this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public boolean isEnabled() {
        return this.state == State.ACTIVATE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


}
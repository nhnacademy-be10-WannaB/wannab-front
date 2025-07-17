package shop.wannab.frontservice.auth.domain;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    public CustomUserDetails(List<GrantedAuthority> authorities) {
        this.id = null;
        this.username = null;
        this.password = null;
        this.state = null;
        this.authorities = authorities;
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
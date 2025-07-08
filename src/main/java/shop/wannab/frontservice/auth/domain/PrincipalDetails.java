package shop.wannab.frontservice.auth.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@RequiredArgsConstructor
public class PrincipalDetails implements OAuth2User {
    private final Long userId;
    private final String role;
    private final boolean isSignedIn;
    private final Map<String, Object> attributes;
    private final String attributeKey;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("Role_" + role));
    }

    @Override
    public String getName() {
        return attributes.get(attributeKey).toString();
    }

}

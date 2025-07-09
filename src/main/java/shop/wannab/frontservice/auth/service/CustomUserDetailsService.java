package shop.wannab.frontservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.auth.CustomUserDetails;
import shop.wannab.frontservice.auth.domain.User;
import shop.wannab.frontservice.auth.exception.DeletedUserException;
import shop.wannab.frontservice.auth.exception.InactiveUserException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthClient authClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authClient.getUsers(username);
        switch (user.getState()) {
            case INACTIVATE -> throw new InactiveUserException(username);
            case DELETED -> throw new DeletedUserException("삭제된 계정입니다");
        }
        return new CustomUserDetails(user);
    }
}

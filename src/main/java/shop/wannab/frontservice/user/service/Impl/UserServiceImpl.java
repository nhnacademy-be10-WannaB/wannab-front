package shop.wannab.frontservice.user.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.user.client.UserClient;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.dto.UserUpdateRequest;
import shop.wannab.frontservice.user.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserPageResponse readUser(){
        ResponseEntity<UserPageResponse> response = userClient.readUser();
        return response.getBody();
    }

    @Override
    public String updateUser(UserUpdateRequest userUpdateRequest){
        String encryptedPassword = passwordEncoder.encode(userUpdateRequest.password());
        UserUpdateRequest request = new UserUpdateRequest(encryptedPassword, userUpdateRequest.name(), userUpdateRequest.email(),
                userUpdateRequest.nickname(), userUpdateRequest.phone());
        ResponseEntity<UserPageResponse> response = userClient.updateUser(request);
        switch (response.getStatusCode()) {
            case HttpStatus.OK -> { return "success"; }
            case HttpStatus.FORBIDDEN -> { return "허가되지 않은 요청입니다."; }
            default -> throw new RuntimeException("예상치 못한 응답입니다: " + response.getStatusCode());
        }
    }

    @Override
    public String deleteUser() {
        ResponseEntity<Void> response = userClient.deleteUser();
        switch (response.getStatusCode()) {
            case HttpStatus.NO_CONTENT -> { return "success"; }
            case HttpStatus.FORBIDDEN -> { return "허가되지 않은 요청입니다."; }
            default -> throw new RuntimeException("예상치 못한 응답입니다: " + response.getStatusCode());
        }
    }
}

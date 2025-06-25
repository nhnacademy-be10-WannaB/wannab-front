package shop.wannab.frontservice.user.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.user.client.UserClient;
import shop.wannab.frontservice.user.dto.UserCreateForm;
import shop.wannab.frontservice.user.dto.UserCreateRequest;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.dto.UserUpdateRequest;
import shop.wannab.frontservice.user.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserClient userClient;


    @Override
    public String createUser(UserCreateForm userCreateForm) {
        UserCreateRequest request = new UserCreateRequest(
                userCreateForm.userId(),
                userCreateForm.password(),
                userCreateForm.username(),
                userCreateForm.email(),
                userCreateForm.phone(),
                userCreateForm.birthday()
        );

//        ResponseEntity<UserResponse> response = userClient.createUser(request);
        ResponseEntity<UserPageResponse> response = userClient.createUser(1L, request);
        switch (response.getStatusCode()) {
            case HttpStatus.CREATED -> { return "success"; }
            case HttpStatus.FORBIDDEN -> { return "허가되지 않은 요청입니다."; }
            case HttpStatus.BAD_REQUEST -> { return "중복된 ID 입니다."; }
            default -> throw new RuntimeException("예상치 못한 응답입니다: " + response.getStatusCode());
        }
    }

    @Override
    public UserPageResponse readUser(){
//        ResponseEntity<UserResponse> response = userClient.readUser(userId);
        ResponseEntity<UserPageResponse> response = userClient.readUser(1L);
        return response.getBody();
    }

    @Override
    public String updateUser(UserUpdateRequest userUpdateRequest){
        ResponseEntity<UserPageResponse> response = userClient.updateUser(1L, userUpdateRequest);
        switch (response.getStatusCode()) {
            case HttpStatus.OK -> { return "success"; }
            case HttpStatus.FORBIDDEN -> { return "허가되지 않은 요청입니다."; }
            default -> throw new RuntimeException("예상치 못한 응답입니다: " + response.getStatusCode());
        }
    }

    @Override
    public String deleteUser() {
//        ResponseEntity<Void> response = userClient.deleteUser();
        ResponseEntity<Void> response = userClient.deleteUser(1L);
        switch (response.getStatusCode()) {
            case HttpStatus.NO_CONTENT -> { return "success"; }
            case HttpStatus.FORBIDDEN -> { return "허가되지 않은 요청입니다."; }
            default -> throw new RuntimeException("예상치 못한 응답입니다: " + response.getStatusCode());
        }
    }
}

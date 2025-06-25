package shop.wannab.frontservice.user.service;

import shop.wannab.frontservice.user.dto.UserCreateForm;
import shop.wannab.frontservice.user.dto.UserPageResponse;


import shop.wannab.frontservice.user.dto.UserUpdateRequest;

public interface UserService {

    // 회원 등록 (Create)
    String createUser(UserCreateForm dto);

    // 회원 단건 조회 (Read)
    UserPageResponse readUser();

    // 회원 정보 수정 (Update)
    String updateUser(UserUpdateRequest userUpdateRequest);

    // 회원 삭제 (Delete)
    String deleteUser();
}

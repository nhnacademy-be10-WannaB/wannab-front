package shop.wannab.frontservice.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.wannab.frontservice.user.dto.UserCreateForm;
import shop.wannab.frontservice.user.dto.UserUpdateRequest;
import shop.wannab.frontservice.user.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/auth/users")
    public String users(){
        return "auth/signup";
    }

    @PostMapping("/auth/users")
    public String createUser(@ModelAttribute @Valid UserCreateForm userCreateDTO,
                             Model model) {
        String errMessage = userService.createUser(userCreateDTO);
        if (!errMessage.equals("success")) {
            model.addAttribute("errMessage", errMessage);
            return "/auth/login";
        }
        return "/user/main";
    }

    @PatchMapping("/users")
    public String updateUser(@ModelAttribute @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        return "/user/mypage-edit";
    }

    @DeleteMapping("/users")
    public String deleteUser() {
        userService.deleteUser();
        return "/user/main";
    }
}

package shop.wannab.frontservice.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.user.dto.AdminPageUserResponse;
import shop.wannab.frontservice.user.dto.AdminUserUpdateRequest;
import shop.wannab.frontservice.user.service.UserService;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping("/admin/users")
    public String adminUser(@RequestParam(defaultValue = "0") int page, HttpServletRequest request, Model model){
        model.addAttribute("currentUri",request.getRequestURI());
        PageResponse<AdminPageUserResponse> adminPageUserResponsePageResponse = userService.readAdminPageUsers(page);

        model.addAttribute("userList", adminPageUserResponsePageResponse.content());
        model.addAttribute("totalPages", adminPageUserResponsePageResponse.totalPages());
        model.addAttribute("hasNext", adminPageUserResponsePageResponse.hasNext());
        model.addAttribute("hasPrevious", adminPageUserResponsePageResponse.hasPrevious());
        model.addAttribute("number", adminPageUserResponsePageResponse.number());

        return "admin/user";
    }

    @GetMapping("/admin/users/new")
    public String createUser(Model model){
        model.addAttribute("currentUri","/admin/user");

        return "admin/user-create-form";
    }

    @GetMapping("/admin/users/update/{loginId}")
    public String updateUser(Model model, @PathVariable("loginId") String loginId) {
        model.addAttribute("currentUri","/admin/user");
        AdminPageUserResponse adminPageUserResponse = userService.readAdminPageUser(loginId);
        model.addAttribute("user", adminPageUserResponse);
        return "admin/user-update-form";
    }

    @PostMapping("/admin/users/update/{loginId}")
    public String updateUser(@PathVariable("loginId") String loginId, AdminUserUpdateRequest adminUserUpdateRequest, Model model){
        userService.updateAdminUser(loginId, adminUserUpdateRequest);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/delete/{loginId}")
    public String deleteUser(@PathVariable("loginId") String loginId){
        userService.deleteAdminUser(loginId);
        return "redirect:/admin/users";
    }
}

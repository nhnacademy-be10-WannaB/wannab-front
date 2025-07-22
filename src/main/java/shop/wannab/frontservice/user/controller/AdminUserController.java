package shop.wannab.frontservice.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequiredArgsConstructor
public class AdminUserController {

    @GetMapping("/admin/user")
    public String adminUser(HttpServletRequest request, Model model){
        model.addAttribute("currentUri",request.getRequestURI());
        return "admin/user";
    }

    @GetMapping("/admin/user/new")
    public String createUser(Model model){
        model.addAttribute("currentUri","/admin/user");

        return "admin/user-create-form";
    }

    @GetMapping("/admin/user/update")
    public String updateUser(Model model){
        model.addAttribute("currentUri","/admin/user");

        return "admin/user-update-form";
    }

    @DeleteMapping("/admin/user/delete")
    public String deleteUser(){
        return "redirect:/admin/user";
    }
}

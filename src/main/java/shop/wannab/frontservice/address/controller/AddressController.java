package shop.wannab.frontservice.address.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.wannab.frontservice.address.dto.*;

import java.util.List;
import shop.wannab.frontservice.address.exception.UserAddressFullException;
import shop.wannab.frontservice.address.service.AddressService;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.user.service.UserService;

@PreAuthorize("hasRole('USER')")
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/mypage-address")
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;


    @GetMapping
    public String addressList(Model model) {
        List<AddressResponse> addresses = addressService.findAllByUserId();
        model.addAttribute("addresses", addresses);
        model.addAttribute("currentUri", "/user/mypage-address");
        UserPageResponse response = userService.readUser();
        UserViewModel viewModel = UserViewModel.builder()
                .id(response.username())
                .password(response.password())
                .phone(response.phone())
                .birth(response.birth())
                .nickname(response.nickname())
                .email(response.email())
                .name(response.name())
                .points(response.points())
                .build();
        model.addAttribute("user", viewModel);
        return "user/mypage-address";
    }

    @GetMapping("/form")
    public String addressForm(Model model) {
        UserPageResponse response = userService.readUser();
        UserViewModel viewModel = UserViewModel.builder()
                .id(response.username())
                .password(response.password())
                .phone(response.phone())
                .birth(response.birth())
                .nickname(response.nickname())
                .email(response.email())
                .name(response.name())
                .build();
        model.addAttribute("user", viewModel);
        model.addAttribute("currentUri", "/user/mypage-address");
        return "user/mypage-address-form";
    }

    @PostMapping
    public String createAddress(@ModelAttribute AddressCreateRequest request) {
        addressService.save(request);
        return "redirect:/user/mypage-address";
    }

    @GetMapping("/{addressId}")
    public String editForm(@PathVariable Long addressId, Model model) {
        AddressResponse address = addressService.findByUserId(addressId);
        model.addAttribute("address", address);
        model.addAttribute("currentUri", "/user/mypage-address");
        UserPageResponse response = userService.readUser();
        UserViewModel viewModel = UserViewModel.builder()
                .id(response.username())
                .password(response.password())
                .phone(response.phone())
                .birth(response.birth())
                .nickname(response.nickname())
                .email(response.email())
                .name(response.name())
                .points(response.points())
                .build();
        model.addAttribute("user", viewModel);

        return "user/mypage-address-edit";
    }

    @PutMapping("/{addressId}")
    public String updateAddress(@PathVariable Long addressId,
                                @ModelAttribute AddressUpdateRequest request) {
        addressService.updateAddress(addressId, request);
        return "redirect:/user/mypage-address";
    }

    @DeleteMapping("/{addressId}")
    public String deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return "redirect:/user/mypage-address";
    }

    @ExceptionHandler(UserAddressFullException.class)
    public String handleUserAddressFullException(UserAddressFullException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "주소는 최대 10개까지 등록할 수 있습니다.");
        return "redirect:/user/mypage-address";
    }

}

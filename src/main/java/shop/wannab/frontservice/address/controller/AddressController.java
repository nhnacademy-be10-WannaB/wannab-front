package shop.wannab.frontservice.address.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.address.dto.*;

import java.util.List;
import shop.wannab.frontservice.address.service.AddressService;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.model.UserViewModel;
import shop.wannab.frontservice.user.service.UserService;


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
        return "user/mypage-address";
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
}

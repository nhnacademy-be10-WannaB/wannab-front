package shop.wannab.frontservice.address.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.address.dto.*;

import java.util.List;
import shop.wannab.frontservice.address.service.AddressService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user/mypage-address")
public class AddressController {

    private final AddressService addressService;

//    @GetMapping
//    public String addressList(Model model) {
//        List<AddressResponse> addresses = addressService.findAllByUserId();
//        model.addAttribute("addresses", addresses);
//        return "user/mypage-address";
//    }


    @PostMapping
    public String createAddress(@ModelAttribute AddressCreateRequest request) {
        addressService.save(request);
        return "redirect:/user/mypage-address";
    }

    @GetMapping("/{addressId}")
    public String editForm(@PathVariable Long addressId, Model model) {
        AddressResponse address = addressService.findByUserId(addressId) ;
        model.addAttribute("address", address);
        return "redirect:/user/mypage-address-edit";
    }

    @PutMapping("/{addressId}")
    public String updateAddress(@PathVariable Long addressId,
                                @ModelAttribute AddressUpdateRequest request) {
        addressService.updateAddress(addressId, request);
        return "user/mypage-address";
    }

    @DeleteMapping("/{addressId}")
    public String deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return "redirect:/user/mypage-address";
    }
}

package shop.wannab.frontservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/book")
    public String bookPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/book";
    }

    @GetMapping("/order")
    public String orderPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/order";
    }

    @GetMapping("/point")
    public String pointPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/point";
    }

    @GetMapping("/delivery-fee")
    public String deliveryPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/delivery-fee";
    }

    @GetMapping("/wrapping-paper")
    public String wrappingPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/wrapping-paper";
    }
}

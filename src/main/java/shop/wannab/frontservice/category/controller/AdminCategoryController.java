package shop.wannab.frontservice.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.wannab.frontservice.category.controller.request.CategoryCreateRequest;
import shop.wannab.frontservice.category.service.AdminCategoryService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping("/new")
    public String addCategory(CategoryCreateRequest request) {

        adminCategoryService.createCategory(request);

        return "redirect:/admin/books";
    }
}

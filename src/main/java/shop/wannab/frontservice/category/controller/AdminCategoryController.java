package shop.wannab.frontservice.category.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.category.controller.request.CategoryCreateCommand;
import shop.wannab.frontservice.category.controller.response.CategoryResponse;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.category.service.AdminCategoryService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    /**
     * 관리자 카테고리 조회 페이지
     * @param parentId 부모 카테고리 ID
     */
    @GetMapping
    public String manageCategories(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "0", name = "childPage") int childPage,
                                   @RequestParam(required = false) Long parentId,
                                   HttpServletRequest request,
                                   Model model) {
        model.addAttribute("currentUri", request.getRequestURI());

        PageResponse<CategoryResponse> parentCategories = adminCategoryService.findAllParentCategories(page);
        model.addAttribute("parentCategories", parentCategories);
        model.addAttribute("pageInfo", parentCategories);
        model.addAttribute("selectedParentId", parentId);
        model.addAttribute("childCategories", new PageResponse<>(List.of(), 0, 0, 0, 0, false, false));

        if (parentId != null) {
            PageResponse<CategoryResponse> childCategories = adminCategoryService.findChildCategoriesByParentId(parentId, childPage);
            model.addAttribute("childCategories", childCategories);
            model.addAttribute("selectedParentId", parentId);
        }

        return "admin/book-category-manage";
    }

    @PostMapping
    public String createParentCategory(@ModelAttribute @Valid CategoryCreateCommand request) {
        adminCategoryService.createParentCategory(request);
        return "redirect:/admin/categories";
    }

    @DeleteMapping
    public String deleteParentCategory(@RequestParam Long categoryId) {
        adminCategoryService.deleteCategory(categoryId);
        return "redirect:/admin/categories";
    }

    @PostMapping("/parents/{parentId}/children")
    public String createChildCategory(@ModelAttribute @Valid CategoryCreateCommand request,
                                      @PathVariable Long parentId) {
        adminCategoryService.createChildCategory(request, parentId);
        return "redirect:/admin/categories?parentId=" + parentId;
    }

    @DeleteMapping("/parents/{parentId}/children")
    public String deleteParentCategory(@PathVariable Long parentId,
                                       @RequestParam Long categoryId) {
        adminCategoryService.deleteCategory(categoryId);
        return "redirect:/admin/categories?parentId=" + parentId;
    }

}

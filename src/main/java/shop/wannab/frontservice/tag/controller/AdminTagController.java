package shop.wannab.frontservice.tag.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.tag.controller.request.TagCreateRequest;
import shop.wannab.frontservice.tag.controller.response.TagResponse;
import shop.wannab.frontservice.tag.service.AdminTagService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tags")
public class AdminTagController {

    private final AdminTagService adminTagService;

    @GetMapping
    public String manageTags(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "") String keyword,
                             HttpServletRequest request,
                             Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("keyword", keyword);

        PageResponse<TagResponse> tags = adminTagService.findAllTags(keyword, page);
        model.addAttribute("tags", tags);

        return "admin/book-tag-management";
    }

    @PostMapping
    public String createTag(@ModelAttribute @Valid TagCreateRequest request) {
        adminTagService.createTag(request);
        return "redirect:/admin/tags";
    }

    @DeleteMapping
    public String deleteTag(@RequestParam Long tagId) {
        adminTagService.deleteTag(tagId);
        return "redirect:/admin/tags";
    }

}

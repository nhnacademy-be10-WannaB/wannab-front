package shop.wannab.frontservice.category.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.wannab.frontservice.category.controller.request.CategoryCreateRequest;
import shop.wannab.frontservice.category.controller.response.CategoryHierarchyDto;
import shop.wannab.frontservice.category.controller.response.ParentCategoryDto;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "categoryClient")
public interface CategoryClient {

    @GetMapping("/api/categories/hierarchy")
    List<CategoryHierarchyDto> getCategoryHierarchy();

    @GetMapping("/api/categories/parents")
    List<ParentCategoryDto> getParentCategory();

    @PostMapping("/api/categories/new")
    void createCategory(CategoryCreateRequest request);

}
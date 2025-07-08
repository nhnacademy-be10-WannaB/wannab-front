package shop.wannab.frontservice.category.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.category.controller.request.CategoryCreateCommand;
import shop.wannab.frontservice.category.controller.request.CategoryCreateRequest;
import shop.wannab.frontservice.category.controller.response.CategoryHierarchyDto;
import shop.wannab.frontservice.category.controller.response.CategoryResponse;
import shop.wannab.frontservice.category.controller.response.ParentCategoryDto;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "categoryClient")
public interface CategoryClient {

    @GetMapping("/api/categories/hierarchy")
    List<CategoryHierarchyDto> getCategoryHierarchy();

    @GetMapping("/api/categories/parents")
    List<ParentCategoryDto> getParentCategory();

    @PostMapping("/api/categories/new")
    void createCategory(@RequestBody CategoryCreateRequest request);

    @GetMapping("/api/categories")
    List<CategoryResponse> findAllParentCategories();

    @GetMapping(value = "/api/categories", params = "parentId")
    List<CategoryResponse> findChildCategoriesByParentId(@RequestParam("parentId") Long parentId);

    @PostMapping("/api/categories")
    ResponseEntity<Void> createParentCategory(@RequestBody CategoryCreateCommand request);

    @PostMapping("/api/categories/{parentId}/children")
    ResponseEntity<Void> createChildCategory(@RequestBody CategoryCreateCommand request,
                             @PathVariable("parentId") Long parentId);

    @DeleteMapping("/api/categories/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId);
}
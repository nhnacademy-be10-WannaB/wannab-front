package shop.wannab.frontservice.category.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import shop.wannab.frontservice.category.controller.response.CategoryHierarchyDto;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "categoryClient")
public interface CategoryClient {

    @GetMapping("/api/categories/hierarchy")
    List<CategoryHierarchyDto> getCategoryHierarchy();

}
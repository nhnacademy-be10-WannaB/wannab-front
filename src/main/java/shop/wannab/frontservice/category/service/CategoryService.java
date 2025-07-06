package shop.wannab.frontservice.category.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.category.client.CategoryClient;
import shop.wannab.frontservice.category.controller.response.CategoryHierarchyDto;
import shop.wannab.frontservice.category.controller.response.ParentCategoryDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryClient categoryClient;

    public List<CategoryHierarchyDto> getCategoryHierarchy() {
        try {
            return categoryClient.getCategoryHierarchy();
        } catch(Exception e) {
            log.error("카테고리 계층 정보를 불러오는 중 오류가 발생했습니다.", e);
            return Collections.emptyList();
        }
    }
}
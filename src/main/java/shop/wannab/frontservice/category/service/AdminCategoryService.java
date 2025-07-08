package shop.wannab.frontservice.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.category.client.CategoryClient;
import shop.wannab.frontservice.category.controller.request.CategoryCreateCommand;
import shop.wannab.frontservice.category.controller.request.CategoryCreateRequest;
import shop.wannab.frontservice.category.controller.response.CategoryResponse;
import shop.wannab.frontservice.category.controller.response.PageResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final CategoryClient categoryClient;

    public void createCategory(CategoryCreateRequest request) {
        try {
            categoryClient.createCategory(request);
            log.info("새로운 카테고리 생성 성공");
        } catch (Exception e) {
            log.error("새로운 카테고리 생성 실패 카테고리 이름 : {}", request.getName(), e);
        }
    }

    public PageResponse<CategoryResponse> findAllParentCategories(int page) {
        return categoryClient.findAllParentCategories(page);
    }

    public PageResponse<CategoryResponse> findChildCategoriesByParentId(Long parentId, int page) {
        return categoryClient.findChildCategoriesByParentId(parentId, page);
    }

    public void createParentCategory(CategoryCreateCommand request) {
        categoryClient.createParentCategory(request);
    }

    public void createChildCategory(CategoryCreateCommand request, Long parentId) {
        categoryClient.createChildCategory(request, parentId);
    }

    public void deleteCategory(Long categoryId) {
        categoryClient.deleteCategory(categoryId);
    }
}
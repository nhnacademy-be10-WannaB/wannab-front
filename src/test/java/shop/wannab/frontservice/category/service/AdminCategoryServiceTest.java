package shop.wannab.frontservice.category.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import shop.wannab.frontservice.category.client.CategoryClient;
import shop.wannab.frontservice.category.controller.request.CategoryCreateCommand;
import shop.wannab.frontservice.category.controller.request.CategoryCreateRequest;
import shop.wannab.frontservice.category.controller.response.CategoryResponse;
import shop.wannab.frontservice.category.controller.response.PageResponse;

@ExtendWith(MockitoExtension.class)
class AdminCategoryServiceTest {

    @Mock
    private CategoryClient categoryClient;

    @InjectMocks
    private AdminCategoryService adminCategoryService;

    private PageResponse<CategoryResponse> pageResponse;

    @BeforeEach
    void setUp() {
        pageResponse = new PageResponse<>(Collections.emptyList(), 0, 0, 0, 0, false, false);
    }

    @Test
    @DisplayName("카테고리 생성")
    void createCategory() {
        doNothing().when(categoryClient).createCategory(any(CategoryCreateRequest.class));

        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setName("test category");
        adminCategoryService.createCategory(request);

        verify(categoryClient).createCategory(any(CategoryCreateRequest.class));
    }

    @Test
    @DisplayName("모든 부모 카테고리 조회")
    void findAllParentCategories() {
        given(categoryClient.findAllParentCategories(anyInt())).willReturn(pageResponse);

        PageResponse<CategoryResponse> response = adminCategoryService.findAllParentCategories(0);

        verify(categoryClient).findAllParentCategories(anyInt());
    }

    @Test
    @DisplayName("부모 ID로 자식 카테고리 조회")
    void findChildCategoriesByParentId() {
        given(categoryClient.findChildCategoriesByParentId(anyLong(), anyInt())).willReturn(pageResponse);

        PageResponse<CategoryResponse> response = adminCategoryService.findChildCategoriesByParentId(1L, 0);

        verify(categoryClient).findChildCategoriesByParentId(anyLong(), anyInt());
    }

    @Test
    @DisplayName("부모 카테고리 생성")
    void createParentCategory() {
        given(categoryClient.createParentCategory(any(CategoryCreateCommand.class))).willReturn(ResponseEntity.ok().build());

        adminCategoryService.createParentCategory(new CategoryCreateCommand("test parent"));

        verify(categoryClient).createParentCategory(any(CategoryCreateCommand.class));
    }

    @Test
    @DisplayName("자식 카테고리 생성")
    void createChildCategory() {
        given(categoryClient.createChildCategory(any(CategoryCreateCommand.class), anyLong())).willReturn(ResponseEntity.ok().build());

        adminCategoryService.createChildCategory(new CategoryCreateCommand("test child"), 1L);

        verify(categoryClient).createChildCategory(any(CategoryCreateCommand.class), anyLong());
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() {
        given(categoryClient.deleteCategory(anyLong())).willReturn(ResponseEntity.ok().build());

        adminCategoryService.deleteCategory(1L);

        verify(categoryClient).deleteCategory(anyLong());
    }
}

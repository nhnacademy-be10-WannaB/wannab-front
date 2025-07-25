package shop.wannab.frontservice.category.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import shop.wannab.frontservice.category.controller.request.CategoryCreateCommand;
import shop.wannab.frontservice.category.controller.response.CategoryResponse;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.category.service.AdminCategoryService;
import shop.wannab.frontservice.global.filter.JwtAuthorizationFilter;

@ActiveProfiles("ci")
@WebMvcTest(controllers = AdminCategoryController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class)
    })
@WithMockUser(roles = "ADMIN")
class AdminCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminCategoryService adminCategoryService;

    private PageResponse<CategoryResponse> pageResponse;

    @BeforeEach
    void setUp() {
        pageResponse = new PageResponse<>(Collections.emptyList(), 0, 0, 0, 0, false, false);
    }

    @Test
    @DisplayName("카테고리 관리 페이지 조회")
    void manageCategories() throws Exception {
        given(adminCategoryService.findAllParentCategories(anyInt())).willReturn(pageResponse);

        mockMvc.perform(get("/admin/categories"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/book-category"))
            .andExpect(model().attributeExists("parentCategories"));
    }

    @Test
    @DisplayName("부모 카테고리 생성")
    void createParentCategory() throws Exception {
        doNothing().when(adminCategoryService).createParentCategory(any(CategoryCreateCommand.class));

        mockMvc.perform(post("/admin/categories").with(csrf())
                .param("name", "test category"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("부모 카테고리 삭제")
    void deleteParentCategory() throws Exception {
        doNothing().when(adminCategoryService).deleteCategory(anyLong());

        mockMvc.perform(delete("/admin/categories").with(csrf())
                .param("categoryId", "1"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("자식 카테고리 생성")
    void createChildCategory() throws Exception {
        doNothing().when(adminCategoryService).createChildCategory(any(CategoryCreateCommand.class), anyLong());

        mockMvc.perform(post("/admin/categories/parents/1/children").with(csrf())
                .param("name", "test child category"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("자식 카테고리 삭제")
    void deleteChildCategory() throws Exception {
        doNothing().when(adminCategoryService).deleteCategory(anyLong());

        mockMvc.perform(delete("/admin/categories/parents/1/children").with(csrf())
                .param("categoryId", "2"))
            .andExpect(status().is3xxRedirection());
    }
}

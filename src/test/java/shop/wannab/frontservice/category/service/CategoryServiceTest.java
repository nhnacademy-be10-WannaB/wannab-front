package shop.wannab.frontservice.category.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import feign.FeignException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.wannab.frontservice.category.client.CategoryClient;
import shop.wannab.frontservice.category.controller.response.CategoryHierarchyDto;
import shop.wannab.frontservice.category.controller.response.ParentCategoryDto;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryClient categoryClient;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 계층 정보 조회 성공")
    void getCategoryHierarchy_success() {
        CategoryHierarchyDto dto1 = new CategoryHierarchyDto();
        dto1.setId(1L);
        dto1.setName("Parent1");
        dto1.setChildren(Collections.emptyList());

        CategoryHierarchyDto dto2 = new CategoryHierarchyDto();
        dto2.setId(2L);
        dto2.setName("Parent2");
        dto2.setChildren(Collections.emptyList());

        List<CategoryHierarchyDto> expectedList = List.of(dto1, dto2);
        given(categoryClient.getCategoryHierarchy()).willReturn(expectedList);

        List<CategoryHierarchyDto> result = categoryService.getCategoryHierarchy();

        assertEquals(expectedList, result);
        verify(categoryClient).getCategoryHierarchy();
    }

    @Test
    @DisplayName("카테고리 계층 정보 조회 실패 - FeignException 발생")
    void getCategoryHierarchy_feignException() {
        given(categoryClient.getCategoryHierarchy()).willThrow(FeignException.class);

        List<CategoryHierarchyDto> result = categoryService.getCategoryHierarchy();

        assertTrue(result.isEmpty());
        verify(categoryClient).getCategoryHierarchy();
    }

    @Test
    @DisplayName("부모 카테고리 조회 성공")
    void getParentCategory_success() {
        ParentCategoryDto dto1 = new ParentCategoryDto();
        dto1.setId(1L);
        dto1.setName("Parent1");

        ParentCategoryDto dto2 = new ParentCategoryDto();
        dto2.setId(2L);
        dto2.setName("Parent2");

        List<ParentCategoryDto> expectedList = List.of(dto1, dto2);
        given(categoryClient.getParentCategory()).willReturn(expectedList);

        List<ParentCategoryDto> result = categoryService.getParentCategory();

        assertEquals(expectedList, result);
        verify(categoryClient).getParentCategory();
    }

    @Test
    @DisplayName("부모 카테고리 조회 실패 - FeignException 발생")
    void getParentCategory_feignException() {
        given(categoryClient.getParentCategory()).willThrow(FeignException.class);

        List<ParentCategoryDto> result = categoryService.getParentCategory();

        assertTrue(result.isEmpty());
        verify(categoryClient).getParentCategory();
    }
}
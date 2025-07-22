package shop.wannab.frontservice.tag.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.global.filter.JwtAuthorizationFilter;
import shop.wannab.frontservice.tag.controller.request.TagCreateRequest;
import shop.wannab.frontservice.tag.controller.response.TagResponse;
import shop.wannab.frontservice.tag.service.AdminTagService;

@ActiveProfiles("ci")
@WebMvcTest(controllers = AdminTagController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class)
    })
@WithMockUser(roles = "ADMIN")
class AdminTagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminTagService adminTagService;

    private PageResponse<TagResponse> pageResponse;

    @BeforeEach
    void setUp() {
        pageResponse = new PageResponse<>(Collections.emptyList(), 0, 0, 0, 0, false, false);
    }

    @Test
    @DisplayName("태그 관리 페이지 조회")
    void manageTags() throws Exception {
        given(adminTagService.findAllTags(anyString(), anyInt())).willReturn(pageResponse);

        mockMvc.perform(get("/admin/tags"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/book-tag-management"))
            .andExpect(model().attributeExists("tags"));
    }

    @Test
    @DisplayName("태그 생성")
    void createTag() throws Exception {
        doNothing().when(adminTagService).createTag(any(TagCreateRequest.class));

        mockMvc.perform(post("/admin/tags").with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "test tag"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("태그 삭제")
    void deleteTag() throws Exception {
        doNothing().when(adminTagService).deleteTag(anyLong());

        mockMvc.perform(delete("/admin/tags").with(csrf())
                .param("tagId", "1"))
            .andExpect(status().is3xxRedirection());
    }
}

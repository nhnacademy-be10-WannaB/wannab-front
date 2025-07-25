package shop.wannab.frontservice.search.controller;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
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
import shop.wannab.frontservice.global.filter.JwtAuthorizationFilter;
import shop.wannab.frontservice.search.service.BookSearchService;

@ActiveProfiles("ci")
@WebMvcTest(controllers = BookSearchController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class)
    })
@WithMockUser
class BookSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookSearchService bookSearchService;

    @Test
    @DisplayName("도서 전체 검색")
    void searchBooks() throws Exception {
        given(bookSearchService.searchTotalBook(anyString(), anySet())).willReturn(Collections.emptyList());

        mockMvc.perform(get("/search/total")
                .param("keyword", "test"))
            .andExpect(status().isOk())
            .andExpect(view().name("public/total-search"))
            .andExpect(model().attributeExists("keyword", "results"));
    }
}

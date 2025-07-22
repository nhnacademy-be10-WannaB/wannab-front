package shop.wannab.frontservice.book.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import shop.wannab.frontservice.auth.service.AuthService;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.controller.request.AladinBookRequest;
import shop.wannab.frontservice.book.controller.request.CreateBookRequest;
import shop.wannab.frontservice.book.controller.request.UpdateBookRequest;
import shop.wannab.frontservice.book.controller.response.SearchBookResponse;
import shop.wannab.frontservice.book.service.AdminBookService;
import shop.wannab.frontservice.book.service.BookService;
import shop.wannab.frontservice.category.service.CategoryService;
import shop.wannab.frontservice.global.response.PageableInfo;
import shop.wannab.frontservice.global.response.SortInfo;

@WebMvcTest(AdminBookController.class)
@ActiveProfiles("ci")
@DisplayName("AdminBookController 테스트")
@WithMockUser(roles = "ADMIN")
class AdminBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminBookService adminBookService;

    @MockBean
    private BookService bookService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private AuthService authService;

    private SearchBookResponse searchBookResponse;
    private AdminBookListResponse adminBookListResponse;

    @BeforeEach
    void setUp() {
        searchBookResponse = new SearchBookResponse("test", 1, 1, 1, "test", Collections.emptyList());
        adminBookListResponse = new AdminBookListResponse(Collections.emptyList(),
                new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1, new SortInfo(true, true, true), 1, true);
    }

    @Test
    @DisplayName("알라딘 도서 검색 페이지 조회")
    void aladinSearchBooks() throws Exception {
        mockMvc.perform(get("/admin/books/aladin"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/aladin-book-form"));
    }

    @Test
    @DisplayName("알라딘 도서 검색")
    void testAladinSearchBooks() throws Exception {
        given(adminBookService.searchBooks(any(SearchRequest.class))).willReturn(searchBookResponse);

        mockMvc.perform(get("/admin/books/aladin/search")
                .param("keyword", "test")
                .param("page", "1"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/aladin-book-form"))
            .andExpect(model().attributeExists("books"));
    }

    @Test
    @DisplayName("도서 관리 페이지 조회")
    void bookPage() throws Exception {
        given(adminBookService.getBooks(anyInt(), anyInt(), anyString())).willReturn(adminBookListResponse);
        given(categoryService.getParentCategory()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/books"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/book"))
            .andExpect(model().attributeExists("books"));
    }

    @Test
    @DisplayName("알라딘 도서 등록")
    void aladinRegisterBook() throws Exception {
        doNothing().when(adminBookService).registerAladinBook(any(AladinBookRequest.class));

        mockMvc.perform(post("/admin/books/aladin")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("keyword", "test")
                        .param("page", "1")
                        .flashAttr("aladinBookRequest", new AladinBookRequest("test", "test", Collections.emptyList(), Collections.emptyList(), "test", "test", 1, "test", "test")))
                        .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("도서 생성 폼 조회")
    void showCreateForm() throws Exception {
        mockMvc.perform(get("/admin/books/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/book-create-form"));
    }

    @Test
    @DisplayName("도서 생성")
    void createBook() throws Exception {
        doNothing().when(adminBookService).createBook(any(CreateBookRequest.class));
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("test title");
        createBookRequest.setBookChapter("test chapter");
        createBookRequest.setDescription("test description");
        createBookRequest.setCategory("test category");
        createBookRequest.setAuthor("test author");
        createBookRequest.setPublisher("test publisher");
        createBookRequest.setPublicationDate(LocalDate.now());
        createBookRequest.setIsbn("test-isbn");
        createBookRequest.setOriginPrice(10000);
        createBookRequest.setStock(10);
        createBookRequest.setStatus(true);
        createBookRequest.setBookTags("test,tags");

        mockMvc.perform(post("/admin/books/register").with(csrf())
                .flashAttr("createBookRequest", createBookRequest))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("도서 수정 폼 조회")
    void showUpdateForm() throws Exception {
        BookDetailResponse bookDetailResponse = new BookDetailResponse(1L, "test", "test", LocalDate.now(), 1, 1, "test", 1, "test", true, List.of("test"), List.of("test"), List.of("test"), List.of("test"), "test");
        given(bookService.getBookDetail(anyLong())).willReturn(bookDetailResponse);

        mockMvc.perform(get("/admin/books/update/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/book-update-form"))
            .andExpect(model().attributeExists("book"));
    }

    @Test
    @DisplayName("도서 수정")
    void updateBook() throws Exception {
        doNothing().when(adminBookService).updateBook(any(UpdateBookRequest.class), anyLong());
        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setTitle("test title");
        updateBookRequest.setBookChapter("test chapter");
        updateBookRequest.setDescription("test description");
        updateBookRequest.setCategory("test category");
        updateBookRequest.setAuthor("test author");
        updateBookRequest.setPublisher("test publisher");
        updateBookRequest.setPublicationDate(LocalDate.now());
        updateBookRequest.setIsbn("test-isbn");
        updateBookRequest.setOriginPrice(10000);
        updateBookRequest.setStock(10);
        updateBookRequest.setStatus(true);
        updateBookRequest.setBookTags("test,tags");

        mockMvc.perform(put("/admin/books/update/1")
                        .with(csrf())
                        .flashAttr("updateBookRequest", updateBookRequest))
                        .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("도서 삭제")
    void deleteBook() throws Exception {
        doNothing().when(adminBookService).deleteBook(anyLong());

        mockMvc.perform(delete("/admin/books/delete/1")
                        .with(csrf()))
                        .andExpect(status().is3xxRedirection());
    }
}
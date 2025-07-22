package shop.wannab.frontservice.book.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.request.CreateBookFeignRequest;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.client.request.UpdateBookFeignRequest;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.SearchResponse;
import shop.wannab.frontservice.book.controller.request.AladinBookRequest;
import shop.wannab.frontservice.book.controller.request.CreateBookRequest;
import shop.wannab.frontservice.book.controller.request.UpdateBookRequest;
import shop.wannab.frontservice.book.controller.response.SearchBookResponse;
import shop.wannab.frontservice.global.minio.BucketType;
import shop.wannab.frontservice.global.minio.MinioService;
import shop.wannab.frontservice.global.response.ApiResponse;
import shop.wannab.frontservice.global.response.PageableInfo;
import shop.wannab.frontservice.global.response.SortInfo;

@ExtendWith(MockitoExtension.class)
class AdminBookServiceTest {

    @Mock
    private AdminBookClient adminBookClient;

    @Mock
    private MinioService minioService;

    @Mock
    private MultipartFile mockMultipartFile;

    @InjectMocks
    private AdminBookService adminBookService;

    private SearchResponse searchResponse;
    private AdminBookListResponse adminBookListResponse;

    @BeforeEach
    void setUp() {
        searchResponse = new SearchResponse("test", 1, 1, 1, "test", "testCategoryId", "testCategoryName", Collections.emptyList());
        adminBookListResponse = new AdminBookListResponse(Collections.emptyList(),
            new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1,
            new SortInfo(true, true, true), 1, true);
    }

    @Test
    @DisplayName("도서 검색")
    void searchBooks() {
        given(adminBookClient.searchFromBookService(any(SearchRequest.class))).willReturn(searchResponse);

        SearchBookResponse response = adminBookService.searchBooks(new SearchRequest("keyword", 1, 10));

        verify(adminBookClient).searchFromBookService(any(SearchRequest.class));
    }

    @Test
    @DisplayName("알라딘 도서 등록")
    void registerAladinBook() {
        given(adminBookClient.createdAladinBook(any(AladinBookRequest.class))).willReturn(ResponseEntity.ok().build());

        adminBookService.registerAladinBook(new AladinBookRequest("test", "test", Collections.emptyList(), Collections.emptyList(), "test", "test", 1, "test", "test"));

        verify(adminBookClient).createdAladinBook(any(AladinBookRequest.class));
    }

    @Test
    @DisplayName("도서 목록 조회")
    void getBooks() {
        given(adminBookClient.getBookList(anyInt(), anyInt(), anyString())).willReturn(new ApiResponse<>("success", adminBookListResponse, null));

        AdminBookListResponse response = adminBookService.getBooks(0, 10, "bookId,desc");

        verify(adminBookClient).getBookList(anyInt(), anyInt(), anyString());
    }

    @Test
    @DisplayName("키워드로 도서 목록 조회")
    void getBooksWithKeyword() {
        given(adminBookClient.getBookList(anyInt(), anyInt(), anyString(), anyString())).willReturn(new ApiResponse<>("success", adminBookListResponse, null));

        AdminBookListResponse response = adminBookService.getBooks(0, 10, "bookId,desc", "keyword");

        verify(adminBookClient).getBookList(anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    @DisplayName("도서 생성")
    void createBook() {
        given(minioService.uploadFiles(any(MultipartFile[].class), any(BucketType.class))).willReturn("image-url");
        given(adminBookClient.createBook(any(CreateBookFeignRequest.class))).willReturn(ResponseEntity.ok(new ApiResponse<>("success", null, null)));

        CreateBookRequest request = new CreateBookRequest();
        request.setBookImages(new MultipartFile[]{mockMultipartFile});
        request.setTitle("test");
        request.setDescription("test");
        request.setPublicationDate(LocalDate.now());
        request.setOriginPrice(1);
        request.setSalesPrice(1);
        request.setStock(1);
        request.setBookChapter("test");
        request.setIsbn("test");
        request.setStatus(true);
        request.setCategory("test");
        request.setAuthor("test");
        request.setPublisher("test");
        request.setBookTags("test");

        adminBookService.createBook(request);

        verify(minioService).uploadFiles(any(MultipartFile[].class), any(BucketType.class));
        verify(adminBookClient).createBook(any(CreateBookFeignRequest.class));
    }

    @Test
    @DisplayName("도서 수정")
    void updateBook() {
        given(minioService.uploadFiles(any(MultipartFile[].class), any(BucketType.class))).willReturn("new-image-url");
        given(adminBookClient.updateBook(anyLong(), any(UpdateBookFeignRequest.class))).willReturn(ResponseEntity.ok(new ApiResponse<>("success", null, null)));

        UpdateBookRequest request = new UpdateBookRequest();
        request.setBookImages(new MultipartFile[]{mockMultipartFile});
        request.setOriginalImageUrls(List.of("existing-image-url"));
        request.setRemoveImages(Collections.emptyList());
        request.setTitle("updated title");
        request.setDescription("updated description");
        request.setPublicationDate(LocalDate.now());
        request.setOriginPrice(2);
        request.setSalesPrice(2);
        request.setStock(2);
        request.setBookChapter("updated chapter");
        request.setIsbn("updated-isbn");
        request.setStatus(false);
        request.setCategory("updated category");
        request.setAuthor("updated author");
        request.setPublisher("updated publisher");
        request.setBookTags("updated,tags");

        adminBookService.updateBook(request, 1L);

        verify(minioService).uploadFiles(any(MultipartFile[].class), any(BucketType.class));
        verify(adminBookClient).updateBook(anyLong(), any(UpdateBookFeignRequest.class));
    }

    @Test
    @DisplayName("도서 삭제")
    void deleteBook() {
        given(adminBookClient.deleteBook(anyLong())).willReturn(ResponseEntity.ok(new ApiResponse<>("success", null, null)));

        adminBookService.deleteBook(1L);

        verify(adminBookClient).deleteBook(anyLong());
    }
}
package shop.wannab.frontservice.book.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBookService {

    private final AdminBookClient adminBookClient;
    private final MinioService minioService;

    public SearchBookResponse searchBooks(SearchRequest searchRequest) {
        SearchResponse searchResponse = adminBookClient.searchFromBookService(searchRequest);
        return SearchBookResponse.from(searchResponse);
    }

    public void registerAladinBook(AladinBookRequest request){
        adminBookClient.createdAladinBook(request);
    }


    public AdminBookListResponse getBooks(int page, int size){
        ApiResponse<AdminBookListResponse> response = adminBookClient.getBookList(page, size);
        return response.data();
    }

    public void createBook(CreateBookRequest request) {
        MultipartFile[] bookImages = request.getBookImages();

        String imageUrls =  minioService.uploadFiles(bookImages, BucketType.BOOK);

        CreateBookFeignRequest feignRequest = new CreateBookFeignRequest(
                request.getTitle(),
                request.getDescription(),
                request.getPublicationDate(),
                request.getOriginPrice(),
                request.getSalesPrice(),
                request.getStock(),
                request.getBookChapter(),
                request.getIsbn(),
                request.getStatus(),
                request.getCategory(),
                request.getAuthor(),
                request.getPublisher(),
                imageUrls,
                request.getBookTags()
        );

        adminBookClient.createBook(feignRequest);
    }

    public void updateBook(UpdateBookRequest request, Long bookId) {

        MultipartFile[] bookImages = request.getBookImages();

        String uploadedImageUrls = minioService.uploadFiles(bookImages, BucketType.BOOK);

        List<String> existing = new ArrayList<>();
        if (request.getOriginalImageUrls() != null) {
            for (String url : request.getOriginalImageUrls()) {
                if (!request.getRemoveImages().contains(url)) {
                    existing.add(url);
                }
            }
        }

        String imageUrls = String.join(", ", existing);
        if (!uploadedImageUrls.isBlank()) {
            imageUrls = imageUrls.isBlank()
                    ? uploadedImageUrls
                    : imageUrls + ", " + uploadedImageUrls;
        }

        UpdateBookFeignRequest feignRequest = new UpdateBookFeignRequest(
                request.getTitle(),
                request.getDescription(),
                request.getPublicationDate(),
                request.getOriginPrice(),
                request.getSalesPrice(),
                request.getStock(),
                request.getBookChapter(),
                request.getIsbn(),
                request.getStatus(),
                request.getCategory(),
                request.getAuthor(),
                request.getPublisher(),
                imageUrls,
                request.getBookTags()
        );

        adminBookClient.updateBook(bookId, feignRequest);
    }



    public void deleteBook(Long bookId){
        adminBookClient.deleteBook(bookId);
    }

}
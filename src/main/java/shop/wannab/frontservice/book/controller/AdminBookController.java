package shop.wannab.frontservice.book.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.client.response.BookDetailResponse;
import shop.wannab.frontservice.book.controller.request.CreateBookRequest;
import shop.wannab.frontservice.book.controller.request.AladinBookRequest;
import shop.wannab.frontservice.book.controller.request.SearchBookRequest;
import shop.wannab.frontservice.book.controller.request.UpdateBookRequest;
import shop.wannab.frontservice.book.controller.response.SearchBookResponse;
import shop.wannab.frontservice.book.service.AdminBookService;
import shop.wannab.frontservice.book.service.BookService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/books")
public class AdminBookController {

    private final AdminBookService adminBookService;
    private final BookService bookService;

    @GetMapping("/aladin")
    public String aladinSearchBooks(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/aladin-book-form";
    }

    @GetMapping("/aladin/search")
    public String aladinSearchBooks(@Valid @ModelAttribute SearchBookRequest searchBookRequest,
                                    HttpServletRequest request,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/error/400";
        }

        model.addAttribute("currentUri", request.getRequestURI());
        int pageSize = 10;
        int page = searchBookRequest.getPage();
        String keyword = searchBookRequest.getKeyword();

        SearchBookResponse response = adminBookService.searchBooks(new SearchRequest(keyword, page, pageSize));

        int totalPages = (int) Math.ceil((double) response.totalResults() / pageSize);
        int visibleRange = 5;

        int startPage = Math.max(1, page - 2);
        int endPage = Math.min(totalPages, startPage + visibleRange - 1);

        if (endPage - startPage < visibleRange - 1) {
            startPage = Math.max(1, endPage - visibleRange + 1);
        }

        model.addAttribute("books", response.items());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("nextPage", page < totalPages ? page + 1 : null);
        model.addAttribute("prevPage", page > 1 ? page - 1 : null);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "admin/aladin-book-form";
    }

    @GetMapping
    public String bookPage(HttpServletRequest request,
                           Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        model.addAttribute("currentUri", request.getRequestURI());

        AdminBookListResponse adminBookListResponse = adminBookService.getBooks(page, size);

        model.addAttribute("books", adminBookListResponse.content());
        model.addAttribute("totalPages", adminBookListResponse.totalPages());
        model.addAttribute("currentPage", adminBookListResponse.number());
        model.addAttribute("totalElements", adminBookListResponse.totalElements());
        model.addAttribute("size", adminBookListResponse.size());

        int totalPages = adminBookListResponse.totalPages();
        int currentPage = adminBookListResponse.number();
        int visibleRange = 5;

        int startPage = Math.max(0, currentPage - (visibleRange / 2));
        int endPage = Math.min(totalPages - 1, startPage + visibleRange - 1);

        if (endPage - startPage < visibleRange - 1) {
            startPage = Math.max(0, endPage - visibleRange + 1);
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prevPage", currentPage > 0 ? currentPage - 1 : 0);
        model.addAttribute("nextPage", currentPage < totalPages - 1 ? currentPage + 1 : totalPages - 1);

        return "admin/book";
    }

    @PostMapping("/aladin")
    public String aladinRegisterBook(@Valid @ModelAttribute AladinBookRequest aladinBookRequest,
                                     @RequestParam String keyword,
                                     @RequestParam(defaultValue = "1") int page,
                                     HttpServletRequest request,
                                     BindingResult bindingResult,
                                     Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/error/400";
        }
        log.info("Request : {}", aladinBookRequest);
        model.addAttribute("currentUri", request.getRequestURI());

        adminBookService.registerAladinBook(aladinBookRequest);

        return "redirect:/admin/books/aladin/search?keyword=" +
                UriUtils.encode(keyword, StandardCharsets.UTF_8) +
                "&page=" + page;
    }



    @GetMapping("/new")
    public String showCreateForm(HttpServletRequest request,
                                 Model model){

        model.addAttribute("currentUri",request.getRequestURI());

        return "admin/book-create-form";
    }

    @PostMapping("/register")
    public String createBook(@Valid @ModelAttribute CreateBookRequest request){
        adminBookService.createBook(request);
        return "redirect:/admin/books";
    }

    @GetMapping ("/update/{bookId}")
    public String showUpdateForm(@PathVariable("bookId") Long bookId,
                                      HttpServletRequest request,
                                      Model model){
        BookDetailResponse book = bookService.getBookDetail(bookId);

        String joinedAuthors = String.join(", ", book.authorNames());
        String joinedPublishers = String.join(", ", book.publisherNames());
        String joinedTags = String.join(", ", book.tagNames());

        model.addAttribute("book",book);
        model.addAttribute("authorName", joinedAuthors);
        model.addAttribute("publisherName", joinedPublishers);
        model.addAttribute("tagName", joinedTags);
        model.addAttribute("currentUri",request.getRequestURI());

        return "admin/book-update-form";
    }

    @PutMapping("/update/{bookId}")
    public String updateBook(@PathVariable("bookId") Long bookId,
                             @Valid @ModelAttribute UpdateBookRequest request){
        adminBookService.updateBook(request,bookId);
        return "redirect:/admin/books";
    }

    @DeleteMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Long bookId){
        adminBookService.deleteBook(bookId);
        return "redirect:/admin/books";
    }
}
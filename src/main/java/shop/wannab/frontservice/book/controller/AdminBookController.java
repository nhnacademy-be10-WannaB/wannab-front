package shop.wannab.frontservice.book.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.controller.request.CreateBookRequest;
import shop.wannab.frontservice.book.controller.request.SearchBookRequest;
import shop.wannab.frontservice.book.controller.response.SearchBookResponse;
import shop.wannab.frontservice.book.service.AdminBookService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/books")
public class AdminBookController {

    private final AdminBookService adminBookService;

    @GetMapping("/aladin")
    public String aladinSearchBooks(HttpServletRequest request, Model model){
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/aladin-book-form";
    }

    @GetMapping("/aladin/search")
    public String aladinSearchBooks(@Valid @ModelAttribute SearchBookRequest searchBookRequest,
                                    HttpServletRequest request,
                                    BindingResult bindingResult,
                                    Model model){
        if(bindingResult.hasErrors()){
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

    @GetMapping("/new")
    public String showCreateForm(HttpServletRequest request,
                                 Model model){

        model.addAttribute("currentUri",request.getRequestURI());

        return "admin/book-create-form";
    }

    @PostMapping("/register")
    public String createBook(@ModelAttribute CreateBookRequest request){
        System.out.println(request);
        return "redirect:/admin/books";
    }

    //수정

    //삭제

}
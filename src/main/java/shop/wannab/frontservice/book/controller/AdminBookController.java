package shop.wannab.frontservice.book.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import shop.wannab.frontservice.book.client.request.SearchRequest;
import shop.wannab.frontservice.book.controller.request.AladinBookRequest;
import shop.wannab.frontservice.book.controller.request.SearchBookRequest;
import shop.wannab.frontservice.book.controller.response.SearchBookResponse;
import shop.wannab.frontservice.book.service.AdminBookService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/books")
public class AdminBookController {

    private final AdminBookService adminBookService;

    @GetMapping
    public String bookPage(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/book";
    }

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

}

package shop.wannab.frontservice.search.controller;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.search.domain.BookSearchField;
import shop.wannab.frontservice.search.dto.response.SearchResultWithSectionResponse;
import shop.wannab.frontservice.search.service.BookSearchService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookSearchController {

    private final BookSearchService bookSearchService;

    @GetMapping("/search/total")
    public String searchBooks(@RequestParam String keyword,
                              @RequestParam(required = false) Set<BookSearchField> fields,
                              Model model) {

        List<SearchResultWithSectionResponse> searchResults = bookSearchService.searchTotalBook(keyword, fields);

        model.addAttribute("keyword", keyword);
        model.addAttribute("results", searchResults);

        return "book/search/total";
    }
}

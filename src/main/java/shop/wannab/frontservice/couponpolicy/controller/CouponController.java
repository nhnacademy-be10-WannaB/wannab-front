package shop.wannab.frontservice.couponpolicy.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.service.AdminBookService;
import shop.wannab.frontservice.couponpolicy.dto.BookCouponInfoDto;
import shop.wannab.frontservice.couponpolicy.client.CouponApiClient;
import shop.wannab.frontservice.couponpolicy.dto.CouponPageDataDto;
import shop.wannab.frontservice.couponpolicy.dto.CouponPolicyCreateDto;

@Controller
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponApiClient couponApiClient;
    private final AdminBookClient adminBookClient;
    private final AdminBookService adminBookService;

    @GetMapping
    public String couponPage(@RequestParam(value = "query", required = false) String query,
                             @PageableDefault(size = 10) Pageable pageable,
                             HttpServletRequest request,
                             Model model) {

        if (query != null && !query.isEmpty()) {
            Page<BookCouponInfoDto> bookPage = adminBookClient.getBookCouponInfoList(
                    query,
                    pageable.getPageNumber(),
                    pageable.getPageSize()
            );
            model.addAttribute("bookPage", bookPage);
            model.addAttribute("query", query);
        } else {
            CouponPageDataDto couponPageDataDto = couponApiClient.getCouponPoliciesPageData();
            model.addAttribute("categoryHierarchy", couponPageDataDto.getCategoryHierarchy());
            model.addAttribute("couponPolicies", couponPageDataDto.getCouponPolicies());
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("couponPolicyCreateDto", new CouponPolicyCreateDto());
        return "admin/coupon";
    }

    @GetMapping("/books")
    public String couponBookPage(HttpServletRequest request,
                                 Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "bookId,desc" ) String sort,
                                 @RequestParam(required = false) String keyword){
        model.addAttribute("currentUri", request.getRequestURI());

        String safeKeyword = (keyword == null) ? "" : keyword;
        AdminBookListResponse adminBookListResponse = adminBookService.getBooks(page, size, sort, safeKeyword);

        model.addAttribute("books", adminBookListResponse.content());
        model.addAttribute("totalPages", adminBookListResponse.totalPages());
        model.addAttribute("currentPage", adminBookListResponse.number());
        model.addAttribute("totalElements", adminBookListResponse.totalElements());
        model.addAttribute("size", adminBookListResponse.size());
        model.addAttribute("keyword", keyword);

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

        return "admin/coupon-book";
    }

    @PostMapping
    public String createCoupon(
            @ModelAttribute CouponPolicyCreateDto requestDto,
            RedirectAttributes redirectAttributes) {
        try {
            couponApiClient.createCouponPolicy(requestDto);
            redirectAttributes.addFlashAttribute("successMessage", "쿠폰 정책이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "쿠폰 정책 등록에 실패했습니다: " + e.getMessage());
        }
        return "redirect:/admin/coupons";
    }

    @DeleteMapping("/{couponPolicyId}")
    public String deleteCouponPolicy(@PathVariable Long couponPolicyId
            , RedirectAttributes redirectAttributes) {
        try {
            couponApiClient.deleteCouponPolicy(couponPolicyId);
            redirectAttributes.addFlashAttribute("successMessage", "쿠폰 정책이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "쿠폰 정책 등록에 실패했습니다.: " + e.getMessage());
        }
        return "redirect:/admin/coupons";
    }
}

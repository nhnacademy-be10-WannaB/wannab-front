package shop.wannab.frontservice.couponpolicy.controller;

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
import shop.wannab.frontservice.book.client.AdminBookClient;
import shop.wannab.frontservice.book.client.response.AdminBookListResponse;
import shop.wannab.frontservice.book.service.AdminBookService;
import shop.wannab.frontservice.couponpolicy.client.CouponApiClient;
import shop.wannab.frontservice.couponpolicy.dto.CouponPageDataDto;
import shop.wannab.frontservice.couponpolicy.dto.CouponPolicyCreateDto;
import shop.wannab.frontservice.couponpolicy.dto.CouponPolicyDto;
import static org.hamcrest.Matchers.hasSize;

import java.util.Collections;
import shop.wannab.frontservice.global.filter.JwtAuthorizationFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(roles = "ADMIN")
@ActiveProfiles("ci")
@WebMvcTest(controllers = CouponController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class)
        })
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponApiClient couponApiClient;

    @MockBean
    private AdminBookService adminBookService;

    @MockBean
    private AdminBookClient adminBookClient;

    private CouponPageDataDto couponPageDataDto;

    @BeforeEach
    void setUp() {
        couponPageDataDto = new CouponPageDataDto();
        couponPageDataDto.setCategoryHierarchy(Collections.emptyList());
        couponPageDataDto.setCouponPolicies(Collections.emptyList());
    }

    @Test
    @DisplayName("쿠폰 관리 페이지 요청")
    void couponPage_shouldReturnCouponPage() throws Exception {
        CouponPolicyDto couponPolicyDto = new CouponPolicyDto();
        couponPolicyDto.setId(1L);
        couponPolicyDto.setName("Test Coupon Policy");
        couponPolicyDto.setDiscountType("PERCENTAGE");
        couponPolicyDto.setCouponType("NORMAL");

        couponPageDataDto.setCouponPolicies(Collections.singletonList(couponPolicyDto));

        given(couponApiClient.getCouponPoliciesPageData()).willReturn(couponPageDataDto);

        mockMvc.perform(get("/admin/coupons"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/coupon"))
                .andExpect(model().attributeExists("couponPolicies"))
                .andExpect(model().attribute("couponPolicies", hasSize(1)));
    }

    @Test
    @DisplayName("쿠폰 적용 도서 선택 페이지 요청")
    void couponBookPage_shouldReturnBookPage() throws Exception {
        AdminBookListResponse mockResponse = new AdminBookListResponse(Collections.emptyList(), null, true, 0, 1, true, 10, 0, null, 0, true);
        given(adminBookService.getBooks(anyInt(), anyInt(), anyString(), anyString())).willReturn(mockResponse);

        mockMvc.perform(get("/admin/coupons/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/coupon-book"))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @DisplayName("쿠폰 정책 생성 성공")
    void createCoupon_shouldRedirectToCouponPage_whenCreationIsSuccessful() throws Exception {
        CouponPolicyCreateDto createDto = new CouponPolicyCreateDto();
        createDto.setName("Test Coupon");
        createDto.setDiscountType("PERCENTAGE");
        createDto.setDiscountValue(10);
        createDto.setCouponType("NORMAL");
        createDto.setPeriodType("DAYS");
        createDto.setValidDays(30);

        doNothing().when(couponApiClient).createCouponPolicy(any(CouponPolicyCreateDto.class));

        mockMvc.perform(post("/admin/coupons")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("couponPolicyCreateDto", createDto)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/coupons"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @DisplayName("쿠폰 정책 생성 실패")
    void createCoupon_shouldRedirectToCouponPage_whenCreationFails() throws Exception {
        CouponPolicyCreateDto createDto = new CouponPolicyCreateDto();
        createDto.setName("Test Coupon");
        createDto.setDiscountType("PERCENTAGE");
        createDto.setDiscountValue(10);
        createDto.setCouponType("NORMAL");
        createDto.setPeriodType("DAYS");
        createDto.setValidDays(30);

        doThrow(new RuntimeException("Creation failed")).when(couponApiClient).createCouponPolicy(any(CouponPolicyCreateDto.class));

        mockMvc.perform(post("/admin/coupons")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("couponPolicyCreateDto", createDto)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/coupons"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @DisplayName("쿠폰 정책 삭제 성공")
    void deleteCouponPolicy_shouldRedirectToCouponPage_whenDeletionIsSuccessful() throws Exception {
        doNothing().when(couponApiClient).deleteCouponPolicy(any(Long.class));

        mockMvc.perform(delete("/admin/coupons/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/coupons"));
    }

    @Test
    @DisplayName("쿠폰 정책 삭제 실패")
    void deleteCouponPolicy_shouldRedirectToCouponPage_whenDeletionFails() throws Exception {
        doThrow(new RuntimeException("Deletion failed")).when(couponApiClient).deleteCouponPolicy(any(Long.class));

        mockMvc.perform(delete("/admin/coupons/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/coupons"));
    }

    @Test
    @DisplayName("생일 쿠폰 발급 성공")
    void issueBirthdayCoupon_shouldRedirectToCouponPage_whenIssuingIsSuccessful() throws Exception {
        doNothing().when(couponApiClient).issueBirthdayCoupon();

        mockMvc.perform(post("/admin/coupons/issue-birthday")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/coupons"));
    }

    @Test
    @DisplayName("생일 쿠폰 발급 실패")
    void issueBirthdayCoupon_shouldRedirectToCouponPage_whenIssuingFails() throws Exception {
        doThrow(new RuntimeException("Issuing failed")).when(couponApiClient).issueBirthdayCoupon();

        mockMvc.perform(post("/admin/coupons/issue-birthday")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/coupons"));
    }
}
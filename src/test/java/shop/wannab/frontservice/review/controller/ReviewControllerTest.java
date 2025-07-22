package shop.wannab.frontservice.review.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Collections;
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
import shop.wannab.frontservice.global.filter.JwtAuthorizationFilter;
import shop.wannab.frontservice.global.response.PageableInfo;
import shop.wannab.frontservice.global.response.SortInfo;
import shop.wannab.frontservice.review.client.response.UserReviewListResponse;
import shop.wannab.frontservice.review.controller.request.CreateReviewRequest;
import shop.wannab.frontservice.review.controller.request.UpdateReviewRequest;
import shop.wannab.frontservice.review.service.ReviewService;
import shop.wannab.frontservice.user.dto.UserPageResponse;
import shop.wannab.frontservice.user.service.UserService;

@ActiveProfiles("ci")
@WebMvcTest(controllers = ReviewController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class)
    })
@WithMockUser(roles = "USER")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserService userService;

    private UserReviewListResponse userReviewListResponse;

    @BeforeEach
    void setUp() {
        userReviewListResponse = new UserReviewListResponse(Collections.emptyList(),
            new PageableInfo(1, 1, new SortInfo(true, true, true), 1, true, true), true, 1, 1, true, 1, 1,
            new SortInfo(true, true, true), 1, true);
    }

    @Test
    @DisplayName("리뷰 생성")
    void createReview() throws Exception {
        doNothing().when(reviewService).createReview(any(CreateReviewRequest.class));

        CreateReviewRequest createReviewRequest = new CreateReviewRequest();
        createReviewRequest.setContent("test content");
        createReviewRequest.setRating(5);
        createReviewRequest.setBookName("test book");
        createReviewRequest.setOrderId(1L);
        createReviewRequest.setBookId(1L);

        mockMvc.perform(post("/user/mypage-review/register").with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("createReviewRequest", createReviewRequest))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("내 리뷰 목록 조회")
    void getMyReviews() throws Exception {
        given(userService.readUser()).willReturn(
            new UserPageResponse("test", "test", "test", "test", LocalDate.now(), "test", "test", 1, "test"));
        given(reviewService.getMyReviews()).willReturn(userReviewListResponse);

        mockMvc.perform(get("/user/mypage-review"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/mypage-review"))
            .andExpect(model().attributeExists("user", "reviews"));
    }

    @Test
    @DisplayName("리뷰 수정")
    void updateReview() throws Exception {
        doNothing().when(reviewService).updateReview(any(UpdateReviewRequest.class));

        UpdateReviewRequest updateReviewRequest = new UpdateReviewRequest();
        updateReviewRequest.setContent("updated content");
        updateReviewRequest.setRating(4);
        updateReviewRequest.setReviewId(1L);

        mockMvc.perform(put("/user/mypage-review/update").with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("updateReviewRequest", updateReviewRequest))
            .andExpect(status().is3xxRedirection());
    }
}

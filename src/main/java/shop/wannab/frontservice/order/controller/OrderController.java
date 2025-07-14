package shop.wannab.frontservice.order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.dto.*;
import shop.wannab.frontservice.order.exception.OrderItemValidationError;
import shop.wannab.frontservice.order.service.CartOrderService;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderApiClient orderApiClient;
    private final CartOrderService cartOrderService;
    @Value("${toss.payments.clientKey}")
    private String clientKey;

    @PostMapping("/user/main-order")
    public String getOrderItems(@CookieValue(value = "guestId", required = false) Long guestId,
                                @CookieValue(value = "access_token", required = false) String accessToken,
                                @ModelAttribute OrderItemListDto orderItemListDto,
                                HttpServletResponse response) {

        if (Objects.isNull(guestId) && Objects.isNull(accessToken)) {//비회원 && 장바구니에 처음 상품 담을시
            GuestCartCookieDto guestCartCookieDto = orderApiClient.createCart();
            cartOrderService.setGuestCookie(guestCartCookieDto, response);
            guestId = guestCartCookieDto.getValue();
        }
        if (orderItemListDto.getOrderItems().size() == 0) {
            log.debug("OrderController : GetOrderItems : orderItemListDto.getOrderItems().size() == 0");
            return "redirect:/user/main-cart";
        }
        orderApiClient.produceOrderPageDto(guestId, orderItemListDto);
        return "redirect:/user/main-order";
    }

    @GetMapping("/user/main-order")
    public String getOrderPage(@CookieValue(value = "guestId", required = false) Long guestId, Model model) {
        log.debug("OrderController::getOrderPage");
        OrderPageRequestDto necesaryOrderInfo = null;
         try {
            necesaryOrderInfo = orderApiClient.consumeOrderPageDto(guestId);
        } catch (FeignException.BadRequest e) {
            List<OrderItemValidationError> errors = parseValidationErrors(e);
            //TODO: 사용자에게 재고부족/판매불가 등 정보 알리고 장바구니로 리다이렉트
        } catch (RuntimeException e) {
            throw new RuntimeException("네트워크 등 문제");
        }

        if (necesaryOrderInfo == null) {
            log.debug("OrderController : /user/main-order dto is Null");
            return "redirect:/user/main-cart"; // 예외 처리
        }

        populateModel(model, necesaryOrderInfo, necesaryOrderInfo.getCustomerId());
        log.debug("After populateModel");
        return "user/main-order";
    }


    @PostMapping("/user/main-order/submit")
    @ResponseBody
    public ResponseEntity<OrderInfoForPayment> processOrder(@CookieValue(value = "guestId", required = false) Long guestId,
                                                            @ModelAttribute OrderSubmitDto orderSubmitDto) {
        try {
            OrderInfoForPayment orderInfoForPayment = orderApiClient.processOrder(guestId, orderSubmitDto);
            return ResponseEntity.ok(orderInfoForPayment);
        } catch (FeignException.BadRequest badRequest) {
            //주문생성 실패시..재고부족 등의 이유로
            return ResponseEntity.badRequest().build();
        }
    }

    private List<OrderItemValidationError> parseValidationErrors(FeignException.BadRequest e) {

        try {
            String json = e.contentUTF8();
            return new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (Exception ex) {
            throw new RuntimeException("검증 오류 응답 파싱 실패", ex);
        }
    }

    private void populateModel(Model model, OrderPageRequestDto dto, Long userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("orderBookInfos", dto.getOrderBookInfoListDto().getOrderBookInfos());
        model.addAttribute("totalBookPrice", dto.getTotalBookPrice());
        model.addAttribute("shippingFee", dto.getShippingFee());
        model.addAttribute("pavingList", dto.getPavingList());
        model.addAttribute("clientKey", clientKey);

        if (userId > 0) { // 회원
            model.addAttribute("userPoints", dto.getUserPoints());
            model.addAttribute("userAddressList", dto.getUserAddressList());
            model.addAttribute("orderCoupons", dto.getOrderCoupons());
        } else {
            model.addAttribute("userPoints", 0);
            model.addAttribute("userAddressList", List.of());
        }
    }
}
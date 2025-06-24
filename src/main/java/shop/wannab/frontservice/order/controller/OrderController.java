package shop.wannab.frontservice.order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.wannab.frontservice.order.client.OrderApiClient;
import shop.wannab.frontservice.order.dto.OrderInfoForPayment;
import shop.wannab.frontservice.order.dto.OrderItemListDto;
import shop.wannab.frontservice.order.dto.OrderPageRequestDto;
import shop.wannab.frontservice.order.dto.OrderSubmitDto;
import shop.wannab.frontservice.order.exception.OrderItemValidationError;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderApiClient orderApiClient;

    @PostMapping("/user/main-order")

    public String getOrderItems(@CookieValue("X-USER-ID") Long userId, @ModelAttribute OrderItemListDto orderItemListDto, HttpSession session) {
        OrderPageRequestDto necesaryOrderInfo = null;

        try {
            necesaryOrderInfo = orderApiClient.getNecesaryOrderInfo(userId, orderItemListDto);
        } catch (FeignException.BadRequest e) {
            List<OrderItemValidationError> errors = parseValidationErrors(e);
            //TODO: 사용자에게 재고부족/판매불가 등 정보 알리고 장바구니로 리다이렉트
        } catch (RuntimeException e) {
            throw new RuntimeException("네트워크 등 문제");
        }
        assert necesaryOrderInfo != null;

        session.setAttribute("orderPageDto", necesaryOrderInfo);

        return "redirect:/user/main-order";
    }

    @GetMapping("/user/main-order")
    public String getOrderPage(@CookieValue("X-USER-ID") Long userId, HttpSession session, Model model) {
        OrderPageRequestDto dto = (OrderPageRequestDto) session.getAttribute("orderPageDto");
        if (dto == null) {
            return "redirect:/user/main-cart"; // 예외 처리
        }
        dto.setUserPoints(1000); //mockData
        populateModel(model, dto, userId);
        return "user/main-order";
    }

    @PostMapping("/user/main-order/submit")
    public String processOrder(@CookieValue("X-USER-ID") Long userId, @ModelAttribute OrderSubmitDto orderSubmitDto) {
        try {
            OrderInfoForPayment orderInfoForPayment = orderApiClient.processOrder(userId, orderSubmitDto);
            long orderId = orderInfoForPayment.getOrderId();
        } catch (FeignException.BadRequest badRequest) {
            //주문생성 실패시..재고부족 등의 이유로
            return "redirect:/user/main";
        }

        //orderInfoForPayment를 가지고 결제로..
        return null;
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
        model.addAttribute("wrappingPaperList", dto.getWrappingPaperList());

        if (userId > 0) { // 회원
            model.addAttribute("userPoints", dto.getUserPoints());
            model.addAttribute("userAddressList", dto.getUserAddressList());
        } else {
            model.addAttribute("userPoints", 0);
            model.addAttribute("userAddressList", List.of());
        }
    }

}

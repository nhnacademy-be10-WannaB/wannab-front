package shop.wannab.frontservice.order.list.ordersManagement.dto;

import java.time.LocalDateTime;
import lombok.Data;


//주문목록조회시 클라이언트에게 보여줄 정보 (현재페이지에선 도서정보도 포함하는데 지워도될듯)
@Data
public class OrderListResponse {
    private Long orderId;
    private LocalDateTime orderAt;    //주문일시
    private OrderStatus orderStatus;  //배송상태
    private LocalDateTime deliveryAt; //출고일
    private int totalPrice;
}

package shop.wannab.frontservice.ordersManagement.dto;


public enum OrderStatus {
    PENDING, //대기
    SHIPPING, //배송중
    COMPLETED, //완료
    RETURNED, //반품
    CANCELLED; //주문취소
}

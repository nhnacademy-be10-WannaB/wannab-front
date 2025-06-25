package shop.wannab.frontservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitDto {

    List<BookOrderSubmitDto> bookOrderSubmitDtos = new ArrayList<>();
    private String userId;
    private Integer usedPoints;
    private LocalDate deliveryRequestAt;
    private String email;
    private String recipientPhoneNumber;
    private String recipientName;
    private String recipientAddress;
    private String guestPassword;


    //TODO: 전체 금액에 적용될 쿠폰정보 추가
}

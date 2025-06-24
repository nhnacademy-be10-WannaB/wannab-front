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
    private String address;
    private LocalDate deliveryRequestAt;

    private String guestName;
    private String guestEmail;
    private String guestPhoneNumber;
    private String guestPassword;
    private String guestAddress;
    private String guestDetailAddress;

    //TODO: 전체 금액에 적용될 쿠폰정보 추가
}

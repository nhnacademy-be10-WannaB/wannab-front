package shop.wannab.frontservice.couponpolicy;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponPolicyDto {
    private long couponPolicyId;
    private String couponPolicyName;
    private String couponType;
    private String discountType;
    private int discountValue;
    private int maxDiscount;
    private int minPurchase;

    private int validDays;
    private LocalDate startDate;
    private LocalDate endDate;

    private String bookName;
    //카테고리 다 붙여서 주기?아니면 맨 하위 카테고리만 주기
    private String categoryName;
    private boolean autoIssue;
}

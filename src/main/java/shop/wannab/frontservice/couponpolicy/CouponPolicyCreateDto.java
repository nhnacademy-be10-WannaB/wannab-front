package shop.wannab.frontservice.couponpolicy;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponPolicyCreateDto {
    private String couponType; // "NORMAL", "BOOK", "CATEGORY"
    private String name;
    private String discountType;
    private int discountValue;
    private int maxDiscount;
    private String periodType;
    private int validDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isBirthday;
    private boolean isWelcome;

    /**
     * [일반 쿠폰용] 최소 주문 금액.
     * 이 필드는 couponType이 'NORMAL'일 때 사용됩니다.
     */
    private Integer minPurchase;

    /**
     * [도서 쿠폰용] 적용 대상 도서의 ID.
     * 이 필드는 couponType이 'BOOK'일 때 사용됩니다.
     */
    private Long targetBookId;

    /**
     * [카테고리 쿠폰용] 적용 대상 카테고리의 ID.
     * 이 필드는 couponType이 'CATEGORY'일 때 사용됩니다.
     */
    private Long targetCategoryId;
}

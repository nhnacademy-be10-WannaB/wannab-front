package shop.wannab.frontservice.couponpolicy;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class CouponPolicyCreateDto {
    private String couponType; // "NORMAL", "BOOK", "CATEGORY"
    private String name;
    private String discountType;
    private Integer discountValue;
    private Integer maxDiscount;
    private String periodType;
    private Integer validDays;

    @JsonFormat
    private LocalDate startDate;

    @JsonFormat
    private LocalDate endDate;

    private boolean isBirthday;
    private boolean isWelcome;

    // --- 조건 필드 분리 ---

    /**
     * [일반 쿠폰용] 최소 주문 금액.
     * 이 필드는 couponType이 'NORMAL'일 때 사용됩니다.
     */
    private Integer minPurchase; // int 대신 Integer를 사용하여 null을 허용

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

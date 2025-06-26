package shop.wannab.frontservice.book.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SearchBookRequest {

    @Min(1)
    private int page = 1;

    @NotBlank(message = "검색어는 비어 있을 수 없습니다.")
    private String keyword = "";
}

package shop.wannab.frontservice.book.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AladinBookRequest(
        @NotBlank String title,
        String category,
        List<String> authors,
        List<String> publishers,
        @NotBlank String publishedDate,
        @NotBlank String isbn,
        @NotNull Integer price,
        @NotBlank String description,
        String thumbnail
) {
}

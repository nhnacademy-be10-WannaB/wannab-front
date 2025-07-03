package shop.wannab.frontservice.book.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.List;
import shop.wannab.frontservice.book.controller.response.BookItem;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchBookItem(
        String title,
        List<String> author,
        LocalDate pubDate,
        String description,
        String isbn,
        String isbn13,
        String itemId,
        Integer priceSales,
        Integer priceStandard,
        String mallType,
        Integer mileage,
        String cover,
        String categoryId,
        String categoryName,
        List<String> publisher
) {
    public BookItem toBookItem() {
        return new BookItem(
                this.title,
                this.categoryName,
                this.author,
                this.publisher,
                this.pubDate,
                this.isbn13 != null ? this.isbn13 : this.isbn,
                this.priceSales != null ? this.priceSales : 0,
                this.description,
                this.cover
        );
    }
}

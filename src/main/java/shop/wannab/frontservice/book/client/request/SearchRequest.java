package shop.wannab.frontservice.book.client.request;

public record SearchRequest(
        String query,
        Integer start,
        Integer maxResults
) {
}

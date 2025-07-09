package shop.wannab.frontservice.tag.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.tag.controller.request.TagCreateRequest;
import shop.wannab.frontservice.tag.controller.response.TagResponse;

@FeignClient(name = "gateway", url = "${gateway.api.url}", path = "/book-service", contextId = "tagClient")
public interface TagClient {

    @GetMapping("/api/tags")
    PageResponse<TagResponse> findAllTags(@RequestParam("keyword") String keyword,
                                          @RequestParam("page") int page);

    @DeleteMapping("/api/tags/{tagId}")
    ResponseEntity<Void> deleteTag(@PathVariable("tagId") Long tagId);

    @PostMapping("/api/tags")
    ResponseEntity<Void> createTag(@RequestBody TagCreateRequest request);
}

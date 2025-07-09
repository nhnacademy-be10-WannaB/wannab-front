package shop.wannab.frontservice.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.tag.client.TagClient;
import shop.wannab.frontservice.tag.controller.request.TagCreateRequest;
import shop.wannab.frontservice.tag.controller.response.TagResponse;

@Service
@RequiredArgsConstructor
public class AdminTagService {

    private final TagClient tagClient;

    public PageResponse<TagResponse> findAllTags(String keyword, int page) {
        return tagClient.findAllTags(keyword, page);
    }

    public void deleteTag(Long tagId) {
        tagClient.deleteTag(tagId);
    }

    public void createTag(TagCreateRequest request) {
        tagClient.createTag(request);
    }
}

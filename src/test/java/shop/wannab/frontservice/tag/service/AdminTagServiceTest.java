package shop.wannab.frontservice.tag.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import shop.wannab.frontservice.category.controller.response.PageResponse;
import shop.wannab.frontservice.tag.client.TagClient;
import shop.wannab.frontservice.tag.controller.request.TagCreateRequest;
import shop.wannab.frontservice.tag.controller.response.TagResponse;

@ExtendWith(MockitoExtension.class)
class AdminTagServiceTest {

    @Mock
    private TagClient tagClient;

    @InjectMocks
    private AdminTagService adminTagService;

    private PageResponse<TagResponse> pageResponse;

    @BeforeEach
    void setUp() {
        pageResponse = new PageResponse<>(Collections.emptyList(), 0, 0, 0, 0, false, false);
    }

    @Test
    @DisplayName("모든 태그 조회")
    void findAllTags() {
        given(tagClient.findAllTags(anyString(), anyInt())).willReturn(pageResponse);

        PageResponse<TagResponse> response = adminTagService.findAllTags("keyword", 0);

        assertEquals(pageResponse, response);
        verify(tagClient).findAllTags(anyString(), anyInt());
    }

    @Test
    @DisplayName("태그 삭제")
    void deleteTag() {
        given(tagClient.deleteTag(anyLong())).willReturn(ResponseEntity.ok().build());

        adminTagService.deleteTag(1L);

        verify(tagClient).deleteTag(anyLong());
    }

    @Test
    @DisplayName("태그 생성")
    void createTag() {
        given(tagClient.createTag(any(TagCreateRequest.class))).willReturn(ResponseEntity.ok().build());

        adminTagService.createTag(new TagCreateRequest("test tag"));

        verify(tagClient).createTag(any(TagCreateRequest.class));
    }
}

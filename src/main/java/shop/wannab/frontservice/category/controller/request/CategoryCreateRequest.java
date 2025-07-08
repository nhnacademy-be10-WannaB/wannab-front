package shop.wannab.frontservice.category.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryCreateRequest {
    private Long parentId;
    private String name;
}

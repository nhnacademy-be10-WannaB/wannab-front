package shop.wannab.frontservice.couponpolicy;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryHierarchyDto {
    private Long id;
    private String name;
    private List<CategoryHierarchyDto> children;
}

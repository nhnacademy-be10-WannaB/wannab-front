package shop.wannab.frontservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointPageResponse {
    private Long id;
    private String policyName;
    private Integer addRate;
    private Integer addPoint;
}

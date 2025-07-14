package shop.wannab.frontservice.user.dto;


import java.time.ZonedDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PointHistoryResponse {
    private Long pointsHistoryId;
    private String pointHistoryReason;
    private int pointHistoryChange;
    private int totalPoints;
    private ZonedDateTime createdAt;
}
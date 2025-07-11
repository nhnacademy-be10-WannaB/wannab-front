package shop.wannab.frontservice.order.list.paving.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PavingResponse {

    private Long id;
    private String name;
    private int price;
}
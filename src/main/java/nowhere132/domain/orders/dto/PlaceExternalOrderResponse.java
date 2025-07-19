package nowhere132.domain.orders.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceExternalOrderResponse {
    private Long orderId;
}
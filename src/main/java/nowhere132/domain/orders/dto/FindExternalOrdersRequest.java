package nowhere132.domain.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindExternalOrdersRequest {
    private String orderId;
    private String statuses;
    private String symbols;
}

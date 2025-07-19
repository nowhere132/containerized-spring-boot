package nowhere132.domain.orders;

import nowhere132.domain.orders.dto.FindExternalOrdersRequest;
import nowhere132.domain.orders.dto.FindExternalOrdersResponse;
import nowhere132.domain.orders.dto.PlaceExternalOrderRequest;
import nowhere132.domain.orders.dto.PlaceExternalOrderResponse;

public interface OrdersUseCase {
    PlaceExternalOrderResponse placeOrder(PlaceExternalOrderRequest request);
    FindExternalOrdersResponse findOrders(FindExternalOrdersRequest request);
}

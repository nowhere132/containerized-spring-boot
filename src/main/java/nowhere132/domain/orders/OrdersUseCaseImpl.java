package nowhere132.domain.orders;

import lombok.RequiredArgsConstructor;
import nowhere132.domain.orders.dto.FindExternalOrdersRequest;
import nowhere132.domain.orders.dto.FindExternalOrdersResponse;
import nowhere132.domain.orders.dto.PlaceExternalOrderRequest;
import nowhere132.domain.orders.dto.PlaceExternalOrderResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersUseCaseImpl implements OrdersUseCase {
    private final OrdersRepository ordersRepository;

    @Override
    public PlaceExternalOrderResponse placeOrder(PlaceExternalOrderRequest request) {
        var order = OrderEntity.builder()
                .side(request.getSide())
                .custodycd(request.getCustodycd())
                .acctno(request.getAcctno())
                .exchange("HSX")
                .board("HSX")
                .symbol(request.getSymbol())
                .quotePrice(request.getPrice())
                .quoteQtty(request.getQtty())
                .remainQtty(request.getQtty())
                .build();
        order = ordersRepository.save(order);
        return PlaceExternalOrderResponse.builder().orderId(order.getId()).build();
    }

    @Override
    public FindExternalOrdersResponse findOrders(FindExternalOrdersRequest request) {
        return null;
    }
}

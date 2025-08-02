package nowhere132.domain.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nowhere132.domain.orders.dto.FindExternalOrdersRequest;
import nowhere132.domain.orders.dto.FindExternalOrdersResponse;
import nowhere132.domain.orders.dto.PlaceExternalOrderRequest;
import nowhere132.domain.orders.dto.PlaceExternalOrderResponse;
import nowhere132.exceptions.HoldException;
import nowhere132.ports.CashOutputPort;
import nowhere132.ports.dto.HoldRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersUseCaseImpl implements OrdersUseCase {
    private final OrdersRepository ordersRepository;
    private final CashOutputPort cashOutputPort;

    @Override
    public PlaceExternalOrderResponse placeOrder(PlaceExternalOrderRequest request) {
        Long feeAmt = request.getQtty() * 2;
        String referenceId = request.getAcctno();
        String holdErrorCode = "";
        try {
            holdErrorCode = cashOutputPort.holdFee(new HoldRequest("place-order", referenceId, feeAmt));
        } catch (InterruptedException e) {
            log.error("Hold fee when {} places {} order {} got interrupted {}", request.getAcctno(), request.getSide(),
                    request.getSymbol(), e.getMessage());
            throw new HoldException("Hold fee failed: -001996");
        } catch (TimeoutException e) {
            log.error("Hold fee timed out when {} places {} order {}", request.getAcctno(), request.getSide(), request.getSymbol());
            throw new HoldException("Hold fee failed: -001999");
        }

        // TODO: handle hold exception and return proper response to users. for now, its returning Internal Server Error
        if (!"0".equals(holdErrorCode)) {
            throw new HoldException("Hold fee failed: " + holdErrorCode);
        }

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

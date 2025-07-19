package nowhere132.domain.orders;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nowhere132.annotations.AlertSlowExecutionTime;
import nowhere132.domain.orders.dto.FindExternalOrdersRequest;
import nowhere132.domain.orders.dto.FindExternalOrdersResponse;
import nowhere132.domain.orders.dto.PlaceExternalOrderRequest;
import nowhere132.domain.orders.dto.PlaceExternalOrderResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Slf4j
public class OrdersController {
    private final OrdersUseCase ordersUseCase;

    @PostMapping()
    private PlaceExternalOrderResponse placeExternalOrder(@RequestBody @Valid PlaceExternalOrderRequest request) {
        return ordersUseCase.placeOrder(request);
    }

    @GetMapping()
    @AlertSlowExecutionTime(thresholdInMillis = 1000)
    private FindExternalOrdersResponse findExternalOrders(FindExternalOrdersRequest request) {
        return ordersUseCase.findOrders(request);
    }
}

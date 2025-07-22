package nowhere132.domain.orders;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nowhere132.annotations.AlertSlowExecutionTime;
import nowhere132.domain.orders.dto.FindExternalOrdersRequest;
import nowhere132.domain.orders.dto.FindExternalOrdersResponse;
import nowhere132.domain.orders.dto.PlaceExternalOrderRequest;
import nowhere132.domain.orders.dto.PlaceExternalOrderResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {
    private final OrdersUseCase ordersUseCase;

    @PostConstruct
    public void debugInit() {
        log.info("Init controller instance: {}", System.identityHashCode(this));
    }

    @PostMapping()
    // Spring CGLIB proxy can't override or route call to a private method
    @AlertSlowExecutionTime(thresholdInMillis = 1000)
    public PlaceExternalOrderResponse placeExternalOrder(@RequestBody @Valid PlaceExternalOrderRequest request) {
        log.info("Handling request with controller: {}", System.identityHashCode(this));
        return ordersUseCase.placeOrder(request);
    }

    @GetMapping()
    public FindExternalOrdersResponse findExternalOrders(FindExternalOrdersRequest request) {
        return ordersUseCase.findOrders(request);
    }
}

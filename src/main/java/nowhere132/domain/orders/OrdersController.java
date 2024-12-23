package nowhere132.domain.orders;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nowhere132.annotations.AlertSlowExecutionTime;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Slf4j
public class OrdersController {
    private OrdersRepository ordersRepository;

    private List<Order> cachedOrders;

    @GetMapping()
    private List<Order> getOrders() {
        return cachedOrders;
    }

    @Async("CustomAsyncExecutor")
    @Scheduled(fixedRate = 5000)
    @AlertSlowExecutionTime(thresholdInMillis = 5000)
    protected void reloadCachedOrders() {
        try {
            var randomDelayInMillis = new Random().nextInt(7000);
            Thread.sleep(randomDelayInMillis);
            cachedOrders = ordersRepository.findAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Reload cache orders interrupted");
        }
    }
}

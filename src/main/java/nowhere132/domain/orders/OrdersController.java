package nowhere132.domain.orders;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {
    private OrdersRepository ordersRepository;

    private List<Order> cachedOrders;

    @GetMapping()
    private List<Order> getOrders() {
        return cachedOrders;
    }

    @Async("CustomAsyncExecutor")
    @Scheduled(fixedRate = 5000)
    protected void reloadCachedOrders() {
        try {
            System.out.println("Reloading cached orders! " + Thread.currentThread().getName() + " at " + LocalDateTime.now());
            var randomDelayInMillis = new Random().nextInt(7000);
            Thread.sleep(randomDelayInMillis);
            cachedOrders = ordersRepository.findAll();
            System.out.println("Reloaded cached orders! " + Thread.currentThread().getName() + " at " + LocalDateTime.now());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Reload cache orders interrupted");
        }
    }
}

package nowhere132.domain.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersRepository ordersRepository;

    private List<Order> cachedOrders;

    @GetMapping()
    private List<Order> getOrders() {
        return cachedOrders;
    }

    @Scheduled(fixedRate = 5000)
    private void reloadCachedOrders() {
        cachedOrders = ordersRepository.findAll();
    }
}

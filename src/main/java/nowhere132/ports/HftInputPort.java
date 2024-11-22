package nowhere132.ports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nowhere132.domain.orders.Order;
import nowhere132.domain.orders.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HftInputPort {
    @Autowired
    private OrdersRepository ordersRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "hft.sync.orders")
    public void listenOrderEvent(String message) {
        System.out.println("Received order event: " + message);

        var order = tryParseOrder(message);
        if (order.isEmpty()) return;

        try {
            ordersRepository.save(order.get());
        } catch (RuntimeException e) {
            System.err.println("Save order failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Optional<Order> tryParseOrder(String message) {
        try {
            var order = mapper.readValue(message, Order.class);
            return Optional.of(order);
        } catch (JsonProcessingException e) {
            System.err.println("Invalid order " + message);
            return Optional.empty();
        }
    }
}

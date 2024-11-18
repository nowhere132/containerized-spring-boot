package nowhere132.adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nowhere132.domain.orders.Order;
import nowhere132.domain.orders.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class HftInputPort {
    @Autowired
    private OrdersRepository ordersRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "hft.sync.orders")
    public void listenOrderEvent(String message) {
        System.out.println("Received order event: " + message);

        try {
            Order order = mapper.readValue(message, Order.class);
            ordersRepository.save(order);
        } catch (JsonProcessingException e) {
            System.err.println("Invalid order " + message);
        } catch (Exception e) {
            System.err.println("Unknown exception for " + message + "\n-->> " + e.getMessage());
        }
    }
}

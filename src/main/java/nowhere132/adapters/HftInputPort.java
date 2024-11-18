package nowhere132.adapters;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class HftInputPort {

    @KafkaListener(topics = "hft.sync.orders")
    public void listenOrderEvent(String message) {
        System.out.println("Received order event: " + message);
    }
}

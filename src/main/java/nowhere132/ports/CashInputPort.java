package nowhere132.ports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nowhere132.ports.enums.RedisResultTask;
import nowhere132.ports.dto.HoldResult;
import nowhere132.ports.dto.RedisResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CashInputPort {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;

    @KafkaListener(topics = "notify.cs.hold-result")
    public void handleHoldResult(String kafkaMessage) {
        var holdResult = parseToHoldResult(kafkaMessage);
        if (holdResult == null) return;

        log.info("Hold result received: {}", holdResult);
        if ("fee".equals(holdResult.type())) {
            var redisResult = new RedisResult(RedisResultTask.HOLD_FEE, holdResult.refId(), holdResult.errorCode());
            try {
                redisTemplate.convertAndSend("result-hold-fee", objectMapper.writeValueAsString(redisResult));
            } catch (JsonProcessingException e) {
                log.error("Failed to encode hold result {}", redisResult);
            }
        }
    }

    private HoldResult parseToHoldResult(String message) {
        try {
            return objectMapper.readValue(message, HoldResult.class);
        } catch (JsonProcessingException e) {
            log.error("Invalid hold result message {}", message);
            return null;
        }
    }
}

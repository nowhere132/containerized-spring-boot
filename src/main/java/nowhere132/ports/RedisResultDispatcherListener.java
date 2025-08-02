package nowhere132.ports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nowhere132.ports.dto.RedisResult;
import nowhere132.ports.enums.RedisResultTask;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisResultDispatcherListener implements MessageListener {
    private final RedisMessageListenerContainer container;
    private final Map<String, BlockingQueue<String>> tasks = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        container.addMessageListener(this, new PatternTopic("result-*"));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            var redisResult = mapper.readValue(message.getBody(), RedisResult.class);
            var taskId = redisResult.taskType() + "." + redisResult.referenceId();
            if (!tasks.containsKey(taskId)) {
                log.warn("Task taskType {} referenceId {} wasn't registered", redisResult.taskType(), redisResult.referenceId());
                return;
            }
            tasks.get(taskId).add(redisResult.errorCode());
        } catch (JsonProcessingException e) {
            log.error("Invalid Redis result message {}", message);
        } catch (IOException e) {
            log.error("I/O exception when receiving Redis result message {}", message);
        }
    }

    public void registerTask(RedisResultTask taskType, String referenceId, BlockingQueue<String> queue) {
        tasks.put(taskType + "." + referenceId, queue);
    }
}

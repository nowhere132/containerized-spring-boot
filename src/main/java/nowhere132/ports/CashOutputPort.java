package nowhere132.ports;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nowhere132.ports.enums.RedisResultTask;
import nowhere132.ports.dto.HoldRequest;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class CashOutputPort {
    private final RedisResultDispatcherListener redisResultDispatcherListener;

    public void holdFeeAsync(HoldRequest request) {
        // TODO: call cash service to hold
    }

    public String holdFee(HoldRequest request) throws InterruptedException, TimeoutException {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
        redisResultDispatcherListener.registerTask(RedisResultTask.HOLD_FEE, request.refId(), blockingQueue);

        holdFeeAsync(request);

        String errorCode = blockingQueue.poll(10, TimeUnit.SECONDS);
        if (errorCode == null) {
            throw new TimeoutException();
        }
        return errorCode;
    }
}

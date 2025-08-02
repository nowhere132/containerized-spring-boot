package nowhere132.ports.dto;

import nowhere132.ports.enums.RedisResultTask;

public record RedisResult(RedisResultTask taskType, String referenceId, String errorCode) {
}

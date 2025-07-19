package nowhere132.aop;

import lombok.extern.slf4j.Slf4j;
import nowhere132.annotations.AlertSlowExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ApplicationAspect {
    @Around("@annotation(anno)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, AlertSlowExecutionTime anno) throws Throwable {
        long startTime = System.currentTimeMillis();
        var proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        if (executionTime <= anno.thresholdInMillis()) return proceed;

        var methodName = joinPoint.getSignature().getName();
        log.warn("{} | {} was executed in {}", Thread.currentThread().getName(), methodName, executionTime);

        return proceed;
    }
}

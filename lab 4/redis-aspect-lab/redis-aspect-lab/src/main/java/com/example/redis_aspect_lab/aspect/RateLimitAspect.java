package com.example.redis_aspect_lab.aspect;

import com.example.redis_aspect_lab.annotation.RateLimit;
import com.example.redis_aspect_lab.exception.RateLimitExceededException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RateLimitAspect {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);
    private final RedisTemplate<String, Object> redisTemplate;

    public RateLimitAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(com.example.redis_aspect_lab.annotation.RateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        // Include client IP or user ID for more accurate rate limiting
        // For simplicity, we're just using method name
        String key = "rate_limit:" + method.getDeclaringClass().getName() + ":" + method.getName();

        logger.info("Checking rate limit for key: {}", key);

        // Get current count
        Long count = redisTemplate.opsForValue().increment(key, 1);
        logger.info("Current count for {}: {}", key, count);

        // Set expiration if this is the first request
        if (count != null && count == 1) {
            redisTemplate.expire(key, rateLimit.duration(), TimeUnit.SECONDS);
            logger.info("Set expiration for key {} to {} seconds", key, rateLimit.duration());
        }

        if (count != null && count > rateLimit.limit()) {
            logger.warn("Rate limit exceeded for method: {}. Count: {}, Limit: {}",
                    method.getName(), count, rateLimit.limit());
            throw new RateLimitExceededException("Rate limit exceeded. Try again later.");
        }

        logger.info("Rate limit check passed for {}", key);
        return joinPoint.proceed();
    }
}
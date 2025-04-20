package com.example.redis_aspect_lab.aspect;

import com.example.redis_aspect_lab.annotation.DistributedLock;
import com.example.redis_aspect_lab.exception.LockAcquisitionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class DistributedLockAspect {
    private static final Logger logger = LoggerFactory.getLogger(DistributedLockAspect.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final ExpressionParser parser = new SpelExpressionParser();

    public DistributedLockAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(com.example.redis_aspect_lab.annotation.DistributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock lockAnnotation = method.getAnnotation(DistributedLock.class);

        String lockKey = "lock:" + evaluateKey(lockAnnotation.key(), method, joinPoint.getArgs());
        boolean acquired = false;

        try {
            acquired = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, "locked",
                    lockAnnotation.expiration(), TimeUnit.SECONDS));

            if (!acquired) {
                logger.warn("Failed to acquire lock for key: {}", lockKey);
                throw new LockAcquisitionException("Operation is currently being processed. Try again later.");
            }

            logger.info("Lock acquired for key: {}", lockKey);
            return joinPoint.proceed();
        } finally {
            if (acquired) {
                redisTemplate.delete(lockKey);
                logger.info("Lock released for key: {}", lockKey);
            }
        }
    }

    private String evaluateKey(String key, Method method, Object[] args) {
        if (!key.contains("#")) {
            return key;
        }

        StandardEvaluationContext context = new StandardEvaluationContext();
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            context.setVariable(parameters[i].getName(), args[i]);
        }

        return parser.parseExpression(key).getValue(context, String.class);
    }
}

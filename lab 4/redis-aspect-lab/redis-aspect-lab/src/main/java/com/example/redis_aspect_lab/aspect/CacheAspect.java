package com.example.redis_aspect_lab.aspect;

import com.example.redis_aspect_lab.annotation.RedisCache;
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
public class CacheAspect {
    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final ExpressionParser parser = new SpelExpressionParser();

    public CacheAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(com.example.redis_aspect_lab.annotation.RedisCache)")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisCache cacheAnnotation = method.getAnnotation(RedisCache.class);

        String cacheKey = "cache:" + evaluateKey(cacheAnnotation.key(), method, joinPoint.getArgs());

        // Try to get from cache
        Object cachedValue = redisTemplate.opsForValue().get(cacheKey);

        if (cachedValue != null) {
            logger.info("Cache hit for key: {}", cacheKey);
            return cachedValue;
        }

        // Execute the method and cache the result
        logger.info("Cache miss for key: {}", cacheKey);
        Object result = joinPoint.proceed();

        if (result != null) {
            redisTemplate.opsForValue().set(cacheKey, result, cacheAnnotation.expiration(), TimeUnit.SECONDS);
            logger.info("Cached result for key: {}", cacheKey);
        }

        return result;
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

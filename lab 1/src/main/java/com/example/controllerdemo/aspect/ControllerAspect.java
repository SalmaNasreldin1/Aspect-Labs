package com.example.controllerdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Before("execution(* com.example.controllerdemo.controller.*.*(..))")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        logger.info("Aspect: Before executing {} method", joinPoint.getSignature().getName());
    }
}

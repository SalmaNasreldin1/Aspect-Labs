package com.example.redis_aspect_lab.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    String key();
    long expiration() default 30; // seconds
    long timeout() default 5; // seconds to wait for lock
}
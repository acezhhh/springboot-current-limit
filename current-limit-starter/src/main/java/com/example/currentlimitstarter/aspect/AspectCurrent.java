package com.example.currentlimitstarter.aspect;

import com.example.currentlimitstarter.annotation.CurrentLimiter;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Aspect
public class AspectCurrent {

    private ConcurrentHashMap<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    @Around("@annotation(com.example.currentlimitstarter.annotation.CurrentLimiter)")
    public Object around(JoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String token = className + "-" + methodName + "rateLimiter";

        CurrentLimiter currentLimiter = getMethodAnnotation((ProceedingJoinPoint) joinPoint, CurrentLimiter.class);

        RateLimiter rateLimiter = rateLimiters.putIfAbsent(token, RateLimiter.create(currentLimiter.QPS()));
        if (rateLimiter == null) {
            rateLimiter = rateLimiters.get(token);
        }
        if (rateLimiter.tryAcquire(1, currentLimiter.timeOut(), TimeUnit.SECONDS)) {
            return ((ProceedingJoinPoint) joinPoint).proceed();
        } else {
            return "没有获取到令牌";
        }
    }

    private <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(clazz);
    }
}

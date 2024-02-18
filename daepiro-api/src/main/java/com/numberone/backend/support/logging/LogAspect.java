package com.numberone.backend.support.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.numberone.backend.domain..*Controller.*(..))")
    public void controller() {
    }

    @Pointcut("execution(* com.numberone.backend.domain..*Service.*(..))")
    public void service() {
    }

    @Before("controller()")
    public void beforeRequest(JoinPoint joinPoint) {
        log.info(" Start request ---> {} ", joinPoint.getSignature().toShortString());
        Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .map(str -> "\t" + str)
                .forEach(log::info);
    }

    @AfterReturning(pointcut = "controller()", returning = "returnValue")
    public void afterReturningLogging(JoinPoint joinPoint, Object returnValue) {
        log.info(" End request ---> {} ", joinPoint.getSignature().toShortString());

        if (Objects.isNull(returnValue)) return;

        log.info("\t response info: {}", returnValue.toString());
    }

    @AfterThrowing(pointcut = "controller()", throwing = "e")
    public void afterThrowingLogging(JoinPoint joinPoint, Exception e) {
        log.error("###Occured error in request {}", joinPoint.getSignature().toShortString());
        log.error("\t{}", e.getMessage());
    }

}

package dev.mrlich.dogeshop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {

    @Pointcut("execution(public * dev.mrlich.dogeshop.service.impl.*.*(..))")
    public void calledAtServiceMethods() {
    }

    @Before("calledAtServiceMethods()")
    public void beforeCalledAtServiceMethods(JoinPoint joinPoint) {
        log.debug("called {} with params {}", joinPoint.toString(), joinPoint.getArgs());
    }

    @AfterReturning(value = "calledAtServiceMethods()", returning = "returned")
    public void afterCalledAtServiceMethods(JoinPoint joinPoint, Object returned) {
        log.debug("finished {} {}", joinPoint.toString(), returned);
    }


}

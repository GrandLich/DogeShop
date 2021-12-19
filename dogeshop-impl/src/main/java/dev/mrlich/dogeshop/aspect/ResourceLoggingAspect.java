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
public class ResourceLoggingAspect {

    @Pointcut("execution(public * dev.mrlich.dogeshop.api.resources.impl.*.*(..))")
    public void calledAtResourceMethods() {
    }

    @Before("calledAtResourceMethods()")
    public void beforeCalledAtResourceMethods(JoinPoint joinPoint) {
        log.info("called {} with params {}", joinPoint.toString(), joinPoint.getArgs());
    }

    @AfterReturning(value = "calledAtResourceMethods()", returning = "returned")
    public void afterCalledAtResourceMethods(JoinPoint joinPoint, Object returned) {
        log.info("finished {} {}", joinPoint.toString(), returned);
    }

}

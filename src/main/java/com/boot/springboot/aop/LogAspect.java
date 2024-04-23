package com.boot.springboot.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Before(value = "within(com.boot.springboot.service.*)")
    public void createStartLog(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature() + "started ");
    }

    @After(value = "execution(public * com.boot.springboot.service.*.*(Long))")
    public void createEndLog(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature() + "Ending");
    }

    @AfterReturning(value = "within(com.boot.springboot.service.*)", returning = "retValue")
    public void LogPrintReturnValue(Object retValue) {
        log.info("Return value" + retValue);
    }

    @AfterThrowing(value = "within(com.boot.springboot.service.*)", throwing = "throwValue")
    public void LogPrintReturnValue(Throwable throwValue) {
        log.error("Throwable value" + throwValue);
    }

    @Around(value = "@annotation(com.boot.springboot.annotation.TimeAop)")
    public void logTime(ProceedingJoinPoint jP) throws Throwable {
        // 1 logica
        //2 start method
        //3 logica
        LocalTime startTime = LocalTime.now();
        jP.proceed();
        LocalTime endTime = LocalTime.now();
        log.info("Methond worked " + (endTime.getNano() - startTime.getNano()));
    }
}

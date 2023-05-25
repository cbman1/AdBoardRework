package ru.kpfu.itis.adboardrework.aspects;


import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.adboardrework.exceptions.NotFoundException;

@Log4j2
@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(public * ru.kpfu.itis.adboardrework.services.impl.*.*(..))")
    public void executionCheck(){

    }

    @AfterThrowing(pointcut = "executionCheck()", throwing = "exception")
    public void logAfterThrowing(NotFoundException exception) {
        log.debug(exception.getMessage());
    }


}

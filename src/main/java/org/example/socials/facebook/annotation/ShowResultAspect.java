package org.example.socials.facebook.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ShowResultAspect {

    @Around("@annotation(ShowResult)")
    public Object showResult(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        System.out.println("--== " + result);
        return result;
    }

}

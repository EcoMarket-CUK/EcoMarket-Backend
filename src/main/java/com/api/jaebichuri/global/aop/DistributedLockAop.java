package com.api.jaebichuri.global.aop;

import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomSocketException;
import com.api.jaebichuri.member.entity.Member;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(value = 1)
@RequiredArgsConstructor
public class DistributedLockAop {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";
    private final RedissonClient redissonClient;

    @Around("@annotation(com.api.jaebichuri.global.aop.DistributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        String key = REDISSON_LOCK_PREFIX + distributedLock.key() + ":" + joinPoint.getArgs()[1];
        Member member = (Member) joinPoint.getArgs()[0];

        log.info("key 이름 : {}", key);

        RLock rLock = redissonClient.getLock(key);

        try {
            boolean getLock = rLock.tryLock(distributedLock.waitTime(),
                distributedLock.leaseTime(), distributedLock.timeUnit());

            log.info("lock 획득 결과 : {}", getLock);

            if (!getLock) {
                throw new CustomSocketException(ErrorStatus._AUCTION_BID_GET_LOCK_FAILED, member.getId());
            }
            return joinPoint.proceed();
        } finally {
            try {
                rLock.unlock();
                log.info("lock 해제");
            } catch (IllegalMonitorStateException e) {
                log.info("이미 락이 해제된 상태입니다.");
            }
        }
    }
}
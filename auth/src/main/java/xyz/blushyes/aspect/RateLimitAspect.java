package xyz.blushyes.aspect;

import static xyz.blushyes.constant.Const.TOKEN_HEADER;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.util.concurrent.RateLimiter;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import xyz.blushyes.ann.RateLimit;
import xyz.blushyes.exception.BaseException;

@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RateLimitAspect {

    private static final String USER_LIMITER_PREFIX = "user:";
    private static final String METHOD_LIMITER_PREFIX = "method:";
    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    @Around("@annotation(xyz.blushyes.ann.RateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = (MethodSignature) joinPoint.getSignature();
        var method = signature.getMethod();
        var rateLimit = method.getAnnotation(RateLimit.class);
        var timeUnit = rateLimit.timeUnit();
        var permitsPerSecond = calculatePermitsPerSecond(rateLimit.permits(), timeUnit);

        RateLimiter rateLimiter;

        if (rateLimit.forUser()) {
            var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                log.error("RateLimit 注解被用在不包含请求的方法上面的时候，开启了 For User 模式");
                return joinPoint.proceed();
            }
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader(TOKEN_HEADER);
            String loginId = (String) StpUtil.getLoginIdByToken(token);
            log.info("RateLimit for user 模式，用户ID：{}", loginId);
            rateLimiter = limiters.computeIfAbsent(USER_LIMITER_PREFIX + loginId, k -> RateLimiter.create(permitsPerSecond));
        } else {
            rateLimiter = limiters.computeIfAbsent(METHOD_LIMITER_PREFIX + method, k -> RateLimiter.create(permitsPerSecond));
        }

        if (!rateLimiter.tryAcquire()) {
            throw new BaseException("服务繁忙请稍后重试", HttpStatus.HTTP_TOO_MANY_REQUESTS);
        }

        return joinPoint.proceed();
    }

    /**
     * 根据时间单位计算每秒允许的请求数
     */
    private double calculatePermitsPerSecond(double permits, TimeUnit timeUnit) {
        // TimeUnit转换为秒
        long seconds = timeUnit.toSeconds(1);
        return permits / seconds;
    }
}
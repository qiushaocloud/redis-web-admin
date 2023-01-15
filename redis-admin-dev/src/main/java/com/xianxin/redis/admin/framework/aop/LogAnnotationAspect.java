package com.xianxin.redis.admin.framework.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xianxin.redis.admin.framework.annotation.LogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Order(-1)
@Slf4j
@Component
public class LogAnnotationAspect {


    @Around("@annotation(ds)")
    public Object logSave(ProceedingJoinPoint joinPoint, LogAnnotation ds) throws Throwable {
        StopWatch watch = new StopWatch();
        watch.start();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogAnnotation logAnnotation = methodSignature.getMethod().getAnnotation(LogAnnotation.class);

        String methodName = joinPoint.getSignature().getName();

        // 包路径
        String packagePath = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        Object result = null;
        List<Object> httpReqArgs = new ArrayList<>();

        String paramsJson = "";
        try {
            log.info("行为：{} - {}", logAnnotation.module(), logAnnotation.business());
            // 记录下请求内容
            log.info("接口地址：{}", request.getRequestURL().toString());
            log.info("接口方法：{}", request.getMethod());
            log.info("访问者IP：{}", request.getRemoteAddr());
            // log.info("包路径：{}", packagePath);

            // 参数值
            Object[] args = joinPoint.getArgs();
            for (Object object : args) {
                if (object instanceof HttpServletRequest) {

                } else if (object instanceof HttpServletResponse) {

                } else {
                    httpReqArgs.add(object);
                }
            }

            try {
                String params = JSONObject.toJSONString(httpReqArgs);
                // 打印请求参数参数
                log.info("接口：{} - 请求参数：{} ", methodName, params);
            } catch (Exception e) {
                log.error("记录参数失败：{}", e.getMessage());
            }

            // 调用原来的方法
            result = joinPoint.proceed();

        } catch (Exception e) {

            throw e;
        } finally {
            watch.stop();
            double time = watch.getTotalTimeSeconds();

            // 获取回执报文及耗时
            String respParameter = JSON.toJSONString(result);
            log.info("接口：{} - 响应参数：{}", methodName, respParameter);
            log.info("接口：{} - 耗时：{}s", methodName, time);
        }

        return result;
    }

}

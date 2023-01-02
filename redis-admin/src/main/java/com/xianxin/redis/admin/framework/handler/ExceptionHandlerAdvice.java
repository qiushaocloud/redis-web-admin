package com.xianxin.redis.admin.framework.handler;

import com.xianxin.redis.admin.framework.common.Response;
import com.xianxin.redis.admin.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import java.net.ConnectException;
import java.nio.file.AccessDeniedException;
import java.util.List;


/**
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({Exception.class})
    public Response<Object> exception(Exception e) {
        log.info("异常堆栈：", e);

        if (e instanceof HttpRequestMethodNotSupportedException) {
            return Response.error(e.getMessage());
        }

        // @Validated 异常拦截
        if (e instanceof MethodArgumentNotValidException) {
            e.printStackTrace();
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            List<ObjectError> ls = bindingResult.getAllErrors();
            String msg = "参数校验失败";

            if (ls.size() > 0) {
                msg = ls.get(0).getDefaultMessage();
            }
            return Response.error(msg);
        }

        if (e instanceof JedisConnectionException) {
            return Response.error(e.getMessage());
        }

        if (e instanceof JedisDataException) {

            if ("value sent to redis cannot be null".equalsIgnoreCase(e.getMessage())) {
                return Response.error("Redis存储失败,值不能为空");
            }
            if ("NOAUTH Authentication required.".equalsIgnoreCase(e.getMessage())) {
                return Response.error("需要密码认证");
            }
            if ("WRONGTYPE Operation against a key holding the wrong kind of value".equalsIgnoreCase(e.getMessage())) {
                return Response.error("键的数据类型错误");
            }
            if ("ERR DB index is out of range".equalsIgnoreCase(e.getMessage())) {
                return Response.error("ERR数据库索引超出范围");
            }
            return Response.error("无效的密码");
        }
        if (e instanceof HttpServerErrorException) {
            //Internal Server Error
            if (e.getMessage().contains("500 Internal Server Error")) {
                return Response.error(500, "内部服务器错误");
            }
        }

        if (e instanceof ConnectException) {
            return Response.error(HttpStatus.SERVICE_UNAVAILABLE.value(), "远程连接被拒绝");
        }

        if (e instanceof NullPointerException) {
            return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务内部空指针异常");
        }

        if (e instanceof HttpMessageNotWritableException) {
            return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "响应数据解析异常");
        }

        if (e instanceof IllegalArgumentException) {
            return Response.error(HttpStatus.BAD_REQUEST.value(), "坏的请求");
        }

        if (e instanceof AccessDeniedException) {
            return Response.error(HttpStatus.FORBIDDEN.value(), "拒绝访问异常");
        }

        if (e instanceof HttpMessageNotReadableException) {
            return Response.error(HttpStatus.BAD_REQUEST.value(), "请求参数不合法，解析出错");
        }

        if (e instanceof ResourceAccessException) {
            return Response.error(e.getMessage());
        }

        if (e instanceof ServerException) {
            ServerException serverException = (ServerException) e;

            return Response.error(serverException.getCode(), serverException.getMessage());
        }

        return Response.error("糟糕，系统好像走神了！");
    }
}

package com.xianxin.redis.admin.framework.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义服务异常
 *
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2019/8/30 15:17
 **/
public class ServerException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    private String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

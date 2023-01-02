package com.xianxin.redis.admin.framework.annotation;

import java.lang.annotation.*;

/**
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2019/8/26 17:46
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    /**
     * 模块
     *
     * @return
     */
    String module();

    /**
     * 业务说明
     *
     * @return
     */
    String business();

    /**
     * 记录执行参数（日志表）
     *
     * @return
     */
    boolean recordRequestParam() default true;

    /**
     * 将用户填写的备注 记录到用户行为表
     *
     * @return
     */
    boolean recordUserBehavior() default false;

    /**
     * 配合 recordUserBehavior=true 使用
     *
     * @return
     */
    String field() default "why";
}

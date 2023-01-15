package com.xianxin.redis.admin.framework.common;

public class ConSts {

    private final static String ROOT = "RA:";

    /**
     * 存储 http请求 配置 关键key
     */
    public final static String HTTP_CLIENT_COMFIG = ROOT + "HTTP_CLIENT_COMFIG";

    /**
     * 存储 任务 关键key
     */
    public static final String TASK_CONFIG = ROOT + "TASK_CONFIG";

    /**
     * 存储 任务组 关键key
     */
    public static final String TASK_GROUP_CONFIG = ROOT + "TASK_GROUP_CONFIG";

    public static final String HOOK_CONFIG = ROOT + "HOOK_CONFIG";

    public static final String TAGS = ROOT + "TAGS";

    public static final String HASH = "hash";

    public static final String STRIGN = "string";

    public static final String LIST = "list";

    public static final String SET = "set";

    public static final String ZSET = "zset";

    public static final String REDIS_DB = "0";
}

server:
  port: <SERVER_PORT>
  tomcat:
    uri-encoding: UTF-8
    max-threads: 1000
    min-spare-threads: 30
    accesslog:
      pattern: common
      enabled: true
      directory: ../logs
      prefix: redis-admin_access_log
      suffix: .log
      request-attributes-enabled: true
      rename-on-rotate: true
logging:
  level:
    root: INFO
    com.xuebusi.cms: DEBUG
    org.apache.shiro.cache.ehcache.EhCacheManager: WARN
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://<DATASOURCE_ADDR>/x-redis-admin?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: <DATASOURCE_USERNAME>
    password: <DATASOURCE_PASSWORD>
    initial-size: 10
    max-active: 100
    min-idle: 10
    max-wait: 60000
    pool-prepared-statements: true
    max-pool-prepared-statement-per-connection-size: 20
    time-between-eviction-runs-millis: 60000
    min-evictable-idle-time-millis: 300000
    #validation-query: SELECT 1 FROM DUAL
    test-while-idle: true
    test-on-borrow: false
    test-on-return: false
    stat-view-servlet:
      enabled: true
      url-pattern: /druid/*
      #login-username: admin
      #login-password: admin
    filter:
      stat:
        log-slow-sql: true
        slow-sql-millis: 1000
        merge-sql: false
      wall:
        config:
          multi-statement-allow: true
  redis:
    open: true  # 是否开启redis缓存  true开启   false关闭
    database: 0
    host: localhost
    port: 6379
    password:     # 密码（默认为空）
    timeout: 6000 # 连接超时时长（毫秒）
    jedis:
      pool:
        max-active: 1000  # 连接池最大连接数（使用负值表示没有限制）
        max-wait: -1ms    # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-idle: 10      # 连接池中的最大空闲连接
        min-idle: 5       # 连接池中的最小空闲连接
  #springMvc的配置
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
    servlet:
      load-on-startup: 1
  #spring-ehcache的配置
  cache:
    type: ehcache
    ehcache:
      config: classpath:ehcache.xml
#mybatis的配置
mybatis:
  config-location: classpath:/mybatis-config.xml
  mapper-locations: classpath:/mappings/**/*.xml
  type-aliases-package: com.xuebusi.cms
#admin页面管理Path
adminPath: /admin
#分页配置
page:
  pageSize: 10
#文件上传的路径
userfiles:
  basedir: /data/redis-admin/upload

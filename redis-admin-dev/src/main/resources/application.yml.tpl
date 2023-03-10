server:
  port: <SERVER_PORT>

# 日志打印位置
logging:
  file:
    #path: /Users/xianxin/Desktop/projects/redis-admin/logs/
    path: /data/logs/redis-admin/
    name: redis-admin.log
spring:
  h2:
    console:
      path: /h2-console
      enabled: true
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:/data/h2db/test;AUTO_SERVER=TRUE
    username: sa
    password:
  jpa:
    database: h2
    show-sql: true
    hibernate:
      ddl-auto: update
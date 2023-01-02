#FROM java:8
FROM openjdk:8-jre-slim

MAINTAINER "1138645967@qq.com"

ENV TZ "Asia/Shanghai"

COPY target/redis-admin.jar /redis-admin.jar

CMD ["--server.port=9898"]

EXPOSE 9898

ENTRYPOINT ["java","-jar","/redis-admin.jar"]

#run image
#docker run -itd --name redis-admin -p 9898:9898 redis-admin

#进入容器内部
#docker exec -it redis-admin bash
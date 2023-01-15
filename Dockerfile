#FROM ubuntu:20.04
FROM majiajue/jdk1.8

ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Shanghai
ENV SERVER_PORT 9898
ENV DATASOURCE_ADDR localhost:3306
ENV RM_AUTH_USERNAME admin
ENV RM_AUTH_PASSWORD rmpassword

USER root

RUN apt update
RUN apt install -y apt-utils
RUN apt install -y procps lsof net-tools lsb-release curl wget lrzsz iputils-ping
RUN apt install -y maven
RUN apt install -y redis-server
RUN apt-get -qq install --no-install-recommends  vim > /dev/null
    
COPY ./redis-admin-dev /app/redis-admin
COPY ./others/maven-settings.xml /usr/share/maven/conf/settings.xml
RUN cd /app/redis-admin \
    && mvn clean install \
    && mvn clean package

COPY ./bootstrap.sh /app/bootstrap.sh

RUN chmod 777 /app/bootstrap.sh \
    && chmod 777 /app/redis-admin/src/main/resources/*.sh \
    && rm -rf /app/redis-admin/src/main/resources/application.yml \
    && echo "set encoding=utf-8" >> /root/.vimrc
    

RUN sed -i "s/^bind 127.0.0.1/#bind 127.0.0.1/" /etc/redis/redis.conf \
    && sed -i "s/^protected-mode yes/protected-mode no/" /etc/redis/redis.conf \
    && sed -i "s/^# requirepass foobared/requirepass redispassword/" /etc/redis/redis.conf

### 可以映射的目录
# VOLUME ["/data"]
# EXPOSE 9898
WORKDIR /app

CMD ["/app/bootstrap.sh"]
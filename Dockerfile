FROM ubuntu:20.04

ENV DEBIAN_FRONTEND noninteractive
ENV TZ "Asia/Shanghai"
ENV DATASOURCE_URL jdbc:mysql://localhost:3306/redis_manager?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8
ENV DATASOURCE_USERNAME root
ENV DATASOURCE_PASSWORD password

RUN apt update
RUN apt install -y apt-utils
RUN apt install -y procps lsof vim net-tools lsb-release curl wget lrzsz
RUN apt install -y openjdk-8-jdk-headless maven
RUN apt install -y redis-server
RUN apt install -y mysql-server --fix-missing --fix-broken
RUN apt install -y iputils-ping

COPY ./redis-admin /app/redis-admin
COPY ./bootstrap.sh /app/bootstrap.sh
COPY ./mysql /app/mysql

# RUN cd /app/redis-admin \
#     && mvn clean package

WORKDIR /app/redis-admin

EXPOSE 9898

### 可以映射的目录
#VOLUME ["/tmp"]

CMD ["/app/bootstrap.sh"]
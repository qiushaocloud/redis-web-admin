FROM ubuntu:20.04

ENV TZ "Asia/Shanghai"

RUN apt update
RUN apt install -y apt-utils
RUN apt install -y procps lsof vim net-tools lsb-release curl wget lrzsz
RUN apt install -y openjdk-8-jdk-headless

COPY ./redis-admin /app/redis-admin

WORKDIR /app/redis-admin

EXPOSE 9898

### 可以映射的目录
#VOLUME ["/tmp"]
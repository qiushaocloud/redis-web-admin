FROM ubuntu:20.04

ENV DEBIAN_FRONTEND noninteractive
ENV TZ "Asia/Shanghai"
ENV SERVER_PORT 80
ENV DATASOURCE_ADDR localhost:3306
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

RUN chmod 777 /app/bootstrap.sh \
    && chmod 777 /app/mysql/*.sh \
    && chmod 777 /app/redis-admin/src/main/resources/*.sh \
    && rm -rf /app/redis-admin/src/main/resources/application-dev.yml \
    && rm -rf /app/mysql/change_root_pwd.sql
    
RUN mv /var/lib/mysql /var/lib/mysql.bak

# RUN cd /app/redis-admin \
#     && mvn clean package

WORKDIR /app/redis-admin

EXPOSE 9898

### 可以映射的目录
#VOLUME ["/data"]

CMD ["/app/bootstrap.sh"]
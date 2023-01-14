#FROM ubuntu:20.04
FROM majiajue/jdk1.8

ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Shanghai
ENV SERVER_PORT 9898
ENV DATASOURCE_ADDR localhost:3306
ENV DATASOURCE_USERNAME root
ENV DATASOURCE_PASSWORD rootmysqlpassword

USER root

RUN apt update
RUN apt install -y apt-utils
RUN apt install -y procps lsof net-tools lsb-release curl wget lrzsz iputils-ping
RUN apt install -y maven
RUN apt install -y redis-server
RUN apt-get -qq install --no-install-recommends  vim > /dev/null
RUN apt-get -qq install -y mysql-server --fix-missing --fix-broken > /dev/null
RUN mkdir -p /var/run/mysqld \
    && chown mysql /var/run/mysqld/
    
COPY ./redis-admin /app/redis-admin
COPY ./maven-settings.xml /usr/share/maven/conf/settings.xml
RUN cd /app/redis-admin \
    && mvn clean package

RUN mkdir -p /opt/tomcat \
    && groupadd tomcat \
    && useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat \
    && cd /tmp \
    && curl -O https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.6/bin/apache-tomcat-8.5.6.tar.gz \
    && tar xzvf apache-tomcat-9*tar.gz -C /opt/tomcat --strip-components=1 \
    && rm -rf apache-tomcat-9*tar.gz \
    && cd /opt/tomcat \
    && chgrp -R tomcat /opt/tomcat \
    && chmod -R g+r conf \
    && chmod g+x conf \
    && chown -R tomcat webapps/ work/ temp/ logs/

COPY ./tomcat.service /etc/systemd/system/tomcat.service
COPY ./bootstrap.sh /app/bootstrap.sh
COPY ./mysql /app/mysql

RUN chmod 777 /app/bootstrap.sh \
    && chmod 777 /app/mysql/*.sh \
    && chmod 777 /app/redis-admin/src/main/resources/*.sh \
    && rm -rf /app/redis-admin/src/main/resources/application-dev.yml \
    && rm -rf /app/mysql/change_root_pwd.sql \
    && echo "set encoding=utf-8" >> /root/.vimrc
    
RUN mv /var/lib/mysql /var/lib/mysql.bak

RUN sed -i "s/^bind 127.0.0.1/#bind 127.0.0.1/" /etc/redis/redis.conf \
    && sed -i "s/^protected-mode yes/protected-mode no/" /etc/redis/redis.conf \
    && sed -i "s/^# requirepass foobared/requirepass redispassword/" /etc/redis/redis.conf \
    && sed -i "s/bind-address/bind-address = 0.0.0.0 #/" /etc/mysql/mysql.conf.d/mysqld.cnf

### 可以映射的目录
#VOLUME ["/data"]
# EXPOSE 9898
WORKDIR /app

CMD ["/app/bootstrap.sh"]
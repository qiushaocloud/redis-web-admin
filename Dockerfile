#FROM ubuntu:20.04
FROM majiajue/jdk1.8

ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Shanghai
ENV DATASOURCE_HOST localhost
ENV DATASOURCE_PORT 3306
ENV DATASOURCE_USERNAME root
ENV DATASOURCE_PASSWORD rootmysqlpassword
ENV RM_AUTH_USERNAME admin
ENV RM_AUTH_PASSWORD rmpassword
ENV RM_AHTH_EMAIL rmadmintest@126.com
ENV MAIL_HOST smtp.126.com
ENV MAIL_USER sendmailtest@126.com
ENV MAIL_PASS sendmailpass
ENV ADMIN_MAIL_RECEIVERS ["recvmailtest01@126.com", "recvmailtest01@126.com"]

USER root

RUN apt update
RUN apt install -y apt-utils
RUN apt install -y procps lsof net-tools lsb-release curl wget lrzsz iputils-ping
RUN apt install -y redis-server
RUN apt-get -qq install --no-install-recommends  vim > /dev/null
RUN apt-get -qq install -y mysql-server --fix-missing --fix-broken > /dev/null
RUN mkdir -p /var/run/mysqld \
    && chown mysql /var/run/mysqld/
RUN apt install -y python2.7 \
    && apt install -y python-pip \
    && apt-get install -y libmysqlclient-dev
RUN apt install -y nginx
    
COPY ./redis-admin /app/redis-admin
COPY ./bootstrap.sh /app/bootstrap.sh
COPY ./mysql /app/mysql
COPY ./redis-admin-nginx.conf /etc/nginx/sites-enabled/default

RUN chmod 777 /app/bootstrap.sh \
    && chmod 777 /app/mysql/*.sh \
    && chmod 777 /app/redis-admin/*.sh \
    && rm -rf /app/redis-admin/conf/conf.py \
    && rm -rf /app/mysql/change_root_pwd.sql \
    && echo "set encoding=utf-8" >> /root/.vimrc

RUN cd /app/redis-admin \
    && pip install -r requirements.txt
    
RUN mv /var/lib/mysql /var/lib/mysql.bak

RUN sed -i "s/^bind 127.0.0.1/#bind 127.0.0.1/" /etc/redis/redis.conf \
    && sed -i "s/^protected-mode yes/protected-mode no/" /etc/redis/redis.conf \
    && sed -i "s/^# requirepass foobared/requirepass redispassword/" /etc/redis/redis.conf \
    && sed -i "s/bind-address/bind-address = 0.0.0.0 #/" /etc/mysql/mysql.conf.d/mysqld.cnf

### 可以映射的目录
#VOLUME ["/data"]

EXPOSE 9898
WORKDIR /app

CMD ["/app/bootstrap.sh"]

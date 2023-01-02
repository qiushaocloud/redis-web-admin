FROM aoyanfei/redis-admin

RUN apt update
RUN apt install -y apt-utils
RUN apt install -y procps lsof vim net-tools lsb-release curl wget lrzsz

### 可以映射的目录
#VOLUME ["/tmp"]

CMD ["java -jar /redis-admin.jar --server.port=9797"]

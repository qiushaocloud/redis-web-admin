#!/bin/bash

echo "start service"

echo "start init application yml"
cd /app/redis-admin/src/main/resources
./init_application_yml.sh
echo "finsh init application yml"

echo "start redis service"
if [ ! -f $TEST_REDIS_PASSWORD ]; then
    echo "need set test redis password"
    sed -i "s/^requirepass.*/requirepass $TEST_REDIS_PASSWORD/g" /etc/redis/redis.conf
fi
# service redis-server start
/usr/bin/redis-server /etc/redis/redis.conf &
echo "finsh redis service"

sleep 5

echo "satrt redis-admin"
cd /app/redis-admin && mvn clean package
cd target
java -jar redis-admin.jar --server.port=$SERVER_PORT
sleep 10
echo "finsh redis-admin"

echo "start while sleep"

while true; do
  #echo "check ...."
  sleep 30
done

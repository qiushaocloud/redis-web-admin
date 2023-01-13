#!/bin/bash

echo "start service"

echo "start init conf py"
cd /app/redis-admin/conf
./init_conf_py.sh
echo "finsh init conf py"

echo "start redis service"
if [ ! -f $TEST_REDIS_PASSWORD ]; then
    echo "need set test redis password"
    sed -i "s/^requirepass.*/requirepass $TEST_REDIS_PASSWORD/g" /etc/redis/redis.conf
fi
# service redis-server start
/usr/bin/redis-server /etc/redis/redis.conf &
echo "finsh redis service"

echo "start mysql service"
cd /app/mysql
./init_mysql.sh
echo "finsh mysql service"

sleep 5

echo "satrt redis-admin"
cd /app/redis-admin
service nginx start
./start.sh start
sleep 10
tail -f log/*.log
echo "finsh redis-admin"

echo "start while sleep"

while true; do
  #echo "check ...."
  sleep 30
done

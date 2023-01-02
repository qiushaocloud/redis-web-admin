#!/bin/bash

echo "start service"

echo "start init application dev yml"
cd /app/redis-admin/src/main/resources
./init_application_dev_yml.sh
echo "finsh init application dev yml"

echo "start redis service"
# service redis-server start
/usr/bin/redis-server /etc/redis/redis.conf &
echo "finsh redis service"

echo "start mysql service"
cd /app/mysql
./init_mysql.sh
echo "finsh mysql service"

sleep 5

# echo "satrt redis-manager"
# cd /app/redis-manager
# ./bin/start.sh
# sleep 10
# tail -f logs/*.log
# echo "finsh redis-manager"

echo "start while sleep"

while true; do
  #echo "check ...."
  sleep 30
done

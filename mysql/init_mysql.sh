#!/bin/bash
echo "run init_mysql.sh"

echo "check /var/lib/mysql/auto.cnf"
if [ ! -f "/var/lib/mysql/auto.cnf" ]; then
  echo "not exist /var/lib/mysql/auto.cnf, need mv /var/lib/mysql.bak to /var/lib/mysql/"
  mkdir -p /var/lib/mysql
  mv /var/lib/mysql.bak/* /var/lib/mysql/
fi
echo 'rm -rf /var/lib/mysql.bak'
rm -rf /var/lib/mysql.bak
chown -R mysql:mysql /var/lib/mysql
chown -R mysql:mysql /var/run/mysqld

echo "start change root password"
echo "DATASOURCE_PASSWORD:$DATASOURCE_PASSWORD"
cp change_root_pwd.sql.tpl change_root_pwd.sql
sed -i "s/<ROOT_PASSWORD>/$DATASOURCE_PASSWORD/g" change_root_pwd.sql

echo "stop mysql service"
service mysql stop
bash stop_mysqld.sh
sleep 5
echo "start mysqld --skip-grant-tables"
mysqld --skip-grant-tables &
sleep 10

IS_EXIST_DB=`mysql < check_is_exist_db.sql | grep x-redis-admin | grep -v grep | wc -l`
echo "IS_EXIST_DB:$IS_EXIST_DB"
if [ $IS_EXIST_DB == 0 ];then
  echo "no exist db, run create db sql"
  mysql < create_db.sql

  echo "run /app/redis-admin/sql/db.sql"
  mysql < /app/redis-admin/sql/db.sql
fi

echo "run change root pwd sql"
mysql < change_root_pwd.sql

echo "stop mysql service"
service mysql stop
bash stop_mysqld.sh
sleep 5

echo "start mysql service"
usermod -d /var/lib/mysql/ mysql
service mysql start
